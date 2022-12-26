import { useState } from "react";
import { useForm } from "react-hook-form";
import { useRouter } from "next/router";
import logo from "../../asset/images/logo.png";
import Image from "next/image";
import axios from "axios";

export default function Signiin() {
  const router = useRouter();
  const { register, handleSubmit } = useForm({ mode: "onChange" });
  const [autoLogCheck, setAutoLogCheck] = useState(null); //자동 로그인 상태
  const form = new FormData();

  const onSubmit = async (data) => {
    form.append("username", data.username);
    form.append("password", data.password);
    console.log("data", data);
    console.log(form);
    const call = async () => {
      const result = await axios.post("/api/login", form);
      return result;
    };
    call().then((data) => console.log(data));
  };

  const checkHandler = (e) => {
    setAutoLogCheck(e.target.checked);
  };

  return (
    <div className="sign" style={{ width: "25%", gap: 0 }}>
      <div className="sign_title">
        <Image src={logo} alt="yehLogo" />
      </div>
      <form onSubmit={handleSubmit(onSubmit)} className="sign_contents">
        <label htmlFor="username">아이디</label>
        <input
          name="username"
          type="text"
          {...register("username")}
          placeholder="아이디를 입력해주세요"
          autoComplete="off"
        />
        <label htmlFor="password">비밀번호</label>
        <input
          name="password"
          type="password"
          {...register("password")}
          placeholder="비밀번호를 입력해주세요"
        />
        <div className="auto_logged">
          <label htmlFor="autoLog" className="auto_logged_label">
            <input
              type="checkbox"
              id="autoLog"
              onChange={(e) => checkHandler(e)}
            />
            <span className="on"></span>
            자동 로그인
          </label>
        </div>
        <button
          type="submit"
          className="sign_Btn"
          style={{ marginTop: "20px" }}
        >
          로그인
        </button>
      </form>
      <div className="sign_Etc_Btn">
        <button>아이디 찾기</button>
        <span>|</span>
        <button>비밀번호 찾기</button>
        <span>|</span>
        <button onClick={() => router.push("/user/signup")}>회원가입</button>
      </div>
    </div>
  );
}
