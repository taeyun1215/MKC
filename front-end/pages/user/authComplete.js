import { useState } from "react";
import { useRouter } from "next/router";
import cookie from "react-cookies";
import axios from "axios";
import { BsCheck2Circle } from "react-icons/bs";

export default function SignupComplete() {
  const router = useRouter();
  return (
    <>
    <div className="sign" style={{ width: "auto" }}>
      <div className="signupComplete">
        <BsCheck2Circle fontSize="72px" color="#2b3089" />
        <h1>이메일인증이 완료 되었습니다.</h1>
        <button onClick={() => router.push("/main")}>홈으로 돌아가기</button>
      </div>
    </div>
    </>
  );
}
