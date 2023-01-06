import { useState } from "react";
import { useForm } from "react-hook-form";
import { useRouter } from "next/router";
import logo from "../../asset/images/logo.png";
import Image from "next/image";
import axios from "axios";
import cookie from "react-cookies";

export default function Signiin() {
  const router = useRouter();
  const { register, handleSubmit } = useForm({ mode: "onChange" });
  const [autoLogCheck, setAutoLogCheck] = useState(null); //자동 로그인 상태
  
  const onSubmit = async (data) => {
    const form = new FormData();

    form.append("username", data.username);
    form.append("password", data.password);

    try {
      await axios
      .post("http://130.162.159.231:8080/api/login", form)
      .then((res) => {
        console.log(res)
        if(res.status === 200) {
          // useQuery('nickname', res.data.nickname)
          setToken(res);
          router.push("/main")
        } else {
          console.log(res)
          alert('아이디나 비밀번호가 일치하지 않습니다. 다시 시도해주세요.')
        }
      })
    } catch(e) {
      console.log(e)
      alert('잠시 후 다시 시도해주세요.')
    }
    
  };
  const setToken = (res) => {
    const accessToken = res.data.access_token;
    const refreshToken = res.data.refresh_token
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    const expires = new Date()
    expires.setDate(Date.now() + 1000 * 60 * 60 * 24)

    cookie.save(
      'accessToken'
      , accessToken
      , {
          path: '/'
          , expires
          // , httpOnly: HTTP_ONLY // dev/prod 에 따라 true / false 로 받게 했다.
      }
  )
  cookie.save(
      'refreshToken'
      , refreshToken
      , {
          path: '/'
          , expires
          // , httpOnly: HTTP_ONLY
      }
  )
  }
  
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
