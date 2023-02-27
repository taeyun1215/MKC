import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { userState } from "../store/states";
import { useRecoilValue , useResetRecoilState } from "recoil";
import { Pagination } from 'antd';
import { EyeOutlined , CommentOutlined, LikeOutlined, FieldTimeOutlined} from "@ant-design/icons";
import cookies from "next-cookies";
import Image from "next/image";

import getToken from "./auth/getToken";
import setToken from "./auth/setToken";
import axios from "axios";

export default function main(props) {
  const reset = useResetRecoilState(userState)
  const loggin = useRecoilValue(userState).loggin
  const router = useRouter();
  const [postsData, setPostsData] = useState([])
  const [rankigData, setRankingData] = useState([])
  const [current, setCurrent] = useState(0);

  if(loggin && props.data === null) {
    reset();
    const Confirm = confirm('세션이 만료되었습니다. 다시 로그인 후 시도해 주세요')
      if(Confirm) router.push("/user/signin");
      else router.push("/");
  } else if(loggin && props.data !== null) {
    setToken(props.data)
  }

  // 실시간 인기글 데이터 통신
  useEffect(() => {
     async function getRankings() {
      const res = await axios.get('/post/popular');
      if(res.data.success) setRankingData(res.data.data)
      else alert('잠시 후 다시 접속해주세요')
    }
    try {
      getRankings();
    } catch(e) {
      console.log(e)
      alert('잠시 후 다시 접속해주세요')
    }
  },[])

  // 전체 글 데이터 통신
  useEffect(() => {
    router.push(`/main?page=${current}`)
    async function getPosts() {
      const res = await axios.get('/post/all', {params : {page : current}});
      if(res.data.success) setPostsData(res.data.data.posts)
      else alert('잠시 후 다시 접속해주세요')
    }  
    try {
      getPosts();
    } catch(e) {
      console.log(e)
      alert('잠시 후 다시 접속해주세요')
    }
  },[current])

  // 글 작성시간 커스텀
  const CreateTime = (value) =>{
    const today = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    if (betweenTime < 1) return '방금전';
    if (betweenTime < 60) {
        return `${betweenTime}분전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
    }

    return `${Math.floor(betweenTimeDay / 365)}년전`;
  } 

  const DetailPosts = (id) => {
    router.push(`/post/${id}`)
  }
  return (
    <>
    <div className="getPost">
        <div className="ranking">
          <p className="rankingTitle">실시간 인기글</p>
          {rankigData.map((i, index) => (
            <div className="rankingContents" key={i.id} >
              <p className="index">{index + 1}</p>
              <p className="title">{i.title}</p>
            </div>
          ))}
        </div>
      <div>
        {postsData.map((i) => (
          <div key={i.id} className="getPostsBox" onClick={() => DetailPosts(i.id)}>
            {CreateTime(i.createTime).indexOf('분전') > 0  || CreateTime(i.createTime).indexOf('시간전') > 0 
              ? <p className="NewPosts">NEW</p>
              : null}
            <div className="mainInfo">
              <div className="mainInfoText">
                <p className="mainInfoTitle">{i.title}</p>  
                <p className="mainInfoContents">{i.content}</p>
                <p className="mainInfoWriter">{i.writer}</p>
              </div>
              {i.image.firstImageUrl !== null ? (
                <div className="ImageInfo">
                  <Image src={i.image.firstImageUrl} width={100} height={100} alt='postImage'/>
                  {i.image.totalImagesCount > 1 ? 
                    <p className="totalImagesCount">{`+${i.image.totalImagesCount - 1}`}</p>
                  : null}
                </div>
              ) : null}
            </div>
            <div className="addInfo">
              <p><EyeOutlined className="addInfoIcons"/> {i.view} Views</p>
              <p><CommentOutlined className="addInfoIcons"/> {i.comments} Comments</p>
              <p><LikeOutlined className="addInfoIcons"/> {i.likes} Likes</p>
              <p><FieldTimeOutlined className="addInfoIcons"/>{CreateTime(i.createTime)}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
      <div className="pagination"> 
        <Pagination current={current} onChange={(page) => setCurrent(page)} total={50} />
      </div>
    </>
  )
}

export async function getServerSideProps(ctx) {
  const allCookies = cookies(ctx);
  if(allCookies.refreshToken) {
    const res = await getToken(allCookies.accessToken, allCookies.refreshToken)
    const data = res.data
    return {
      props: {
        name : "main",
        data : data  
      },
    };
  } else return {
    props: {
      name : "main",
      data : null  
    },
  } 
}