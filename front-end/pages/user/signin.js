import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useRouter } from "next/router";
import logo from "../../asset/images/logo.png";
import Image from "next/image";
import axios from "axios";
import { useDispatch } from "react-redux";
import { nickname, emailAuth, isLog } from "../../reducer/user";
import { useCookies } from "react-cookie";
import cookie from "react-cookies";
import { HTTP_ONLY } from "../../config/config";

export default function Signiin(props) {
  const router = useRouter();
  const dispatch = useDispatch();
  const { register, handleSubmit } = useForm({ mode: "onChange" });
  const [cookies, setCookie, removeCookie] = useCookies();
  const [autoLogCheck, setAutoLogCheck] = useState(null); //자동 로그인 상태

  const onSubmit = async (data) => {
    const form = new FormData();

    form.append("username", data.username);
    form.append("password", data.password);
    try {
      await axios.post("/api/login", form).then((res) => {
        console.log(res)
        if (res.data.success === true) {
          dispatch(isLog(true));
          // dispatch(nickname(res.data.nickname));
          // dispatch(emailAuth(res.data.emailVerified));
          const accessToken = res.data.data.access_token;
          const refreshToken = res.data.data.refresh_token;
          axios.defaults.headers.common[
            "Authorization"
          ] = `Bearer ${accessToken}`;
          // setCookie("accessToken", accessToken);
          // setCookie("refreshToken", refreshToken);
          cookie.save("accessToken", accessToken, {
            httpOnly: HTTP_ONLY,
            path: "/",
          });
          router.push("/");
        } else {
          console.log(res);
          // alert(res.data.error[0].message);
        }
      });
    } catch (e) {
      console.log(e);
      alert("잠시 후 다시 시도해주세요.");
    }
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
        <button onClick={() => router.push('/user/idFind')}>아이디 찾기</button>
        <span>|</span>
        <button>비밀번호 찾기</button>
        <span>|</span>
        <button onClick={() => router.push("/user/signup")}>회원가입</button>
      </div>
    </div>
  );
}
