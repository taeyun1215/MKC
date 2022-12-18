import { BsCheck2Circle } from "react-icons/bs";
import { useRouter } from "next/router";

export default function signupComplete() {
  const router = useRouter();

  return (
    <div className="sign" style={{ width: "auto" }}>
      <div className="signupComplete">
        <BsCheck2Circle fontSize="72px" color="#2b3089" />
        <h1>회원가입이 완료 되었습니다.</h1>
        <span>YEH의 모든 기능 사용을 위해 이메일 인증을 완료해 주세요.</span>
        <button>이메일 인증</button>
      </div>
    </div>
  );
}
