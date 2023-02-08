import { useForm } from "react-hook-form";
import { useRouter } from "next/router";
import logo from "../../asset/images/logo.png";
import Image from "next/image";
import axios from "axios";
import cookie from "react-cookies";
import { HTTP_ONLY } from "../../config/config";
import { userState } from "../../store/states";
import { useSetRecoilState } from "recoil";

export default function Signiin(props) {
  const router = useRouter();
  const setUser = useSetRecoilState(userState)

  const { register, handleSubmit } = useForm({ mode: "onChange" });

  const expires = new Date()
  expires.setMinutes(expires.getMinutes() + 1);
  // expires.setDate(Date.now() + 1000 * 60)
  const onSubmit = async (data) => {
    const formData = new FormData();
    formData.append("username", data.username);
    formData.append("password", data.password);
    try {
      await axios.post("/api/login", formData).then((res) => {
        console.log(res)
        if (res.data.success === true) {
          const response = res.data.data;
          setUser({name : response.nickname , loggin : true, emailAuth : response.emailVerified})
          const accessToken = response.access_token;
          const refreshToken = response.refresh_token;
          axios.defaults.headers.common[
            "Authorization"
          ] = `Bearer ${accessToken}`;
          cookie.save("accessToken", accessToken, {
            httpOnly: HTTP_ONLY,
            path: "/",
            expires : expires
            
          });
          cookie.save("refreshToken", refreshToken, {
            httpOnly: HTTP_ONLY,
            path: "/",
          });
          router.push("/");
        } else {
          console.log(res);
          alert(res.data.error.message);
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
        {/* <div className="auto_logged">
          <label htmlFor="autoLog" className="auto_logged_label">
            <input
              type="checkbox"
              id="autoLog"
              onChange={(e) => checkHandler(e)}
            />
            <span className="on"></span>
            자동 로그인
          </label>
        </div> */}
        <button
          type="submit"
          className="sign_Btn"
          style={{ marginTop: "50px" }}
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
export async function getServerSideProps(ctx) {
return {
    props: {
      name : "signin",
      data : null  
    },
  } 
}