import { BsCheck2Circle } from "react-icons/bs";
import { useRouter } from "next/router";
import "semantic-ui-css/semantic.min.css";
import cookie from "react-cookies";
import axios from "axios";

export default function SignupComplete() {
  const router = useRouter();

  const handleOnAuth = async () => {
    const token = cookie.load("userToken");
    await axios
      .delete("http://130.162.159.231:8080/email/certify-regis", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => console.log(res));
  };
  return (
    <div className="sign" style={{ width: "auto" }}>
      <div className="signupComplete">
        <BsCheck2Circle fontSize="72px" color="#2b3089" />
        <h1>회원가입이 완료 되었습니다.</h1>
        <span>YEH의 모든 기능 사용을 위해 이메일 인증을 완료해 주세요.</span>
        <button onClick={handleOnAuth}>이메일 인증</button>
      </div>
    </div>
  );
}
