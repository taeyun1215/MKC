import { useState } from "react";
import { useRouter } from "next/router";
import cookie from "react-cookies";
import axios from "axios";
import { BsCheck2Circle } from "react-icons/bs";
import { Spin, Modal } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';

export default function SignupComplete() {
  const [isModal, setIsModal] = useState(true)
  const [authConfirm, setAuthConfirm] = useState(false)
  const router = useRouter();

  const handleOnAuth = () => {
    setIsModal(true)
    const token = cookie.load("userToken");
    try{
      axios.post("http://130.162.159.231:8080/email/certify-regis", {
        body : null
      }, {
      headers: {
            "Authorization" : `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
        })
        .then((res) => {
          console.log(res)
          if(res.data.msg === 'ok') {
            alert('이메일 인증이 완료되었습니다. 로그인 후 서비스 이용이 가능합니다.');
            setIsModal(false);
            setAuthConfirm(true);
          } else {
            alert('잠시 후 다시 시도해 주세요.');
            setIsModal(false)
          }
        });
    } catch(e) {
      console.log(e)
      alert('잠시 후 다시 시도해 주세요.');
      setIsModal(false)
    }
  };
  const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

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
 
     {authConfirm ?
        <Modal title="이메일 인증" open={isModal} onCancel={() => setIsModal(false)} width='420px'  cancelButtonProps={{ style: { display: 'none' } }} 
        okText='확인' onOk={() => router.push("/main")}>
        <p>인증이 확인되었습니다<br/>로그인 후 서비스를 이용해주시기 바랍니다.</p>
      </Modal> :
        <Modal title="이메일 인증" open={isModal} onCancel={() => setIsModal(false)} width='420px' footer={null}>
        <p>인증 메일이 전송되었습니다<br/>전송된 메일을 통해 인증해주시기 바랍니다</p>
        <Spin indicator={antIcon}/>
      </Modal>
    } 
    
    </>
  );
}
