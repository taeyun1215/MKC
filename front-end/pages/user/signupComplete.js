import { useState } from "react";
import { useRouter } from "next/router";
import cookie from "react-cookies";
import axios from "axios";
import { BsCheck2Circle } from "react-icons/bs";
import {  Modal } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';

export default function SignupComplete() {
  const [isModal, setIsModal] = useState(false)
  const router = useRouter();

  const handleOnAuth = () => {
    const token = cookie.load("userToken");
    try{
      axios.post("http://130.162.159.231:8080/email/certify-regis", {
        body : null
      }, {
      headers: {
            "Authorization" : `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
        }).then((res) => {
          if(res.data.msg === 'ok') {
             setIsModal(true);
             cookie.remove('userToken', {path : '/'}, 1000)
          } else {
            alert('잠시 후 다시 시도해 주세요.');
          }
        }).catch((err) => {
          console.log(err);
          alert('잠시 후 다시 시도해 주세요.');
        });
    } catch(e) {
      console.log(e)
      alert('잠시 후 다시 시도해 주세요.');
    }
  };
  const handleOnOk = () => {
    setIsModal(false);
    router.push("/");
  }
  return (
    <>
    <div className="sign" style={{ width: "auto" }}>
      <div className="signupComplete">
        <BsCheck2Circle fontSize="72px" color="#2b3089" />
        <h1>회원가입이 완료 되었습니다.</h1>
        <span>YEH의 모든 기능 사용을 위해 이메일 인증을 완료해 주세요.</span>
        <button onClick={handleOnAuth}>이메일 인증</button>
      </div>
    </div>
   
     {isModal ?
      <Modal title="이메일 인증" open={isModal} onOk={handleOnOk} onCancel={() => setIsModal(false)} cancelButtonProps={{ style: { display: 'none' } }} width='420px'>
        <p>인증 메일이 전송되었습니다<br/>전송된 메일을 통해 인증해주시기 바랍니다</p>
      </Modal> 
      : null} 
    </>
  );
}
