import cookies from "next-cookies";
import axios from "axios";
import getToken from "../auth/getToken";
import { useRouter } from "next/router";
import { userState } from "../../store/states";
import { useRecoilValue } from "recoil";
import { useEffect, useState } from "react";
import { Button, Upload} from 'antd';
import { InboxOutlined } from "@ant-design/icons";


export default function Post(props) {
  const user = useRecoilValue(userState)
  const router = useRouter();
  const { Dragger } = Upload;
  const [images, setImages] = useState([]);


  useEffect(() => {
    if(!user.loggin) {
      const logginConfirm = confirm('로그인 후 이용 가능합니다. 로그인 페이지로 이동합니다.');
      if(logginConfirm) {
        router.push("/user/signin");
      } else router.push("/");
    } else if(user.loggin && !user.emailAuth) {
      const emailAuthConfirm = confirm('YEH의 모든 기능 사용을 위해 이메일 인증을 완료해 주세요.');
        if(emailAuthConfirm) {
          router.push("/user/signupComplete");
        } else router.push("/");
    }
  }, [])

  const handleOnSubmit = async () => {
    const formData = new FormData()
    images.forEach((image) => formData.append('files', image))
    // formData.append(
    //   'data',
    //   JSON.stringify({
    //       title: titleInput,
    //       content: contentInput,
    //   }),
    // );
    console.log(formData)
    try {
        // axios를 이용한 post 요청. 헤더를 multipart/form-data 로 한다.
        await axios.post('/post/new', formData, {
            headers: {'Content-Type': 'multipart/form-data', charset: 'utf-8'},
        });
        alert('게시글이 등록되었습니다');
    } catch (err) {
        // alert(err.data.message || '게시글 등록에 실패했습니다');
    }
  }

  const handleFileChange = async (event) => {
    setImages([...images, ...event.fileList])
    // setImages([...images, ...event.target.files])
  };

  // https://maruzzing.github.io/study/react/%EB%A6%AC%EC%95%A1%ED%8A%B8%EB%A1%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%8D%94-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0/
  // https://velog.io/@hjkdw95/next-s3-upload%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%98%EC%97%AC-%EB%8B%A4%EC%A4%91-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0

  const uploads = {
    name: 'file',
    multiple: true,
  }

  const handleOnCancle = () => {
    console.log('test')
  }

  return (  
    <div className="post">
        <input
          type="text"
          placeholder="제목을 입력해 주세요"
          name="title"
          autoComplete="off"
        />
        <textarea placeholder="내용을 입력해 주세요" />
        {/* <div className="postFile">
          <label htmlFor="file">첨부파일</label>          
          <input type="file" id="file" multiple="multiple" accept="image/jpg,image/png,image/jpeg,image/gif"  onChange={handleFileChange} />
        </div> */}
      <Dragger {...uploads} onChange={handleFileChange}>
        <div style={{display:'flex', justifyContent:'center', gap:'5px'}}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">파일 업로드</p>
        </div>
        <p className="ant-upload-hint">
         첨부할 파일을 선택하거나 마우스로 드래그 해주세요.
        </p>
      </Dragger>
     
      <div className="postBtn">
        <button className="cancle" onClick={handleOnCancle}>취소</button>
        <button onClick={handleOnSubmit}>등록</button>
      </div>
    
    </div>
  );
}

export async function getServerSideProps(ctx) {
  const allCookies = cookies(ctx);
  const res = await getToken(allCookies.accessToken, allCookies.refreshToken)
  const data = res.data
  return {
    props: {
      name : "board",
      data : data  
    },
  };
}