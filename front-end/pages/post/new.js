import cookies from "next-cookies";
import cookie from "react-cookies";
import axios from "axios";
import getToken from "../auth/getToken";
import { useRouter } from "next/router";
import { userState } from "../../store/states";
import { useRecoilValue } from "recoil";
import { useEffect, useState } from "react";
import { Button, Upload} from 'antd';
import { StarOutlined, InboxOutlined } from "@ant-design/icons";


export default function Post(props) {
  const user = useRecoilValue(userState)
  const router = useRouter();
  const { Dragger } = Upload;
  const [title, setTitle] = useState('');
  const [contents, setContents] = useState('');
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
    const token = cookie.load("accessToken");
    const formData = new FormData()
    images.forEach((image) => formData.append('imageFiles', image))
    formData.append(
      'data',
      JSON.stringify({
          title: title,
          content: contents,
      }),
    );
    try {
        // axios를 이용한 post 요청. 헤더를 multipart/form-data 로 한다.
        await axios.post('/post/new', formData, {
            headers: {
            'Authorization' : `Bearer ${token}`,
            'Content-Type': 'multipart/form-data',
            charset: 'utf-8'
          },
        }).then((res) => console.log(res));
        // alert('게시글이 등록되었습니다');
    } catch (err) {
        console.log(err)
        // alert(err.data.message || '게시글 등록에 실패했습니다');
    }
  }

  const handleFileChange = async (event) => {
    setImages([...images, ...event.fileList])
  };

  const uploads = {
    name: 'file',
    multiple: true
  }
  
  const handleOnCancle = () => {
    setTitle('');
    setContents('')
  }

  return (  
    <div className="post">
        <input
          type="text"
          placeholder="제목을 입력해 주세요"
          name="title"
          autoComplete="off"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <textarea placeholder="내용을 입력해 주세요" 
          value={contents}
          onChange={(e) => setContents(e.target.value)} />
        <Dragger {...uploads} onChange={handleFileChange} style={{backgroundColor:'red'}}>
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
  if(allCookies.refreshToken) {
    const res = await getToken(allCookies.accessToken, allCookies.refreshToken)
    const data = res.data
    return {
      props: {
        name : "post",
        data : data  
      },
    };
  } else return {
    props: {
      name : "post",
      data : null  
    },
  } 
}