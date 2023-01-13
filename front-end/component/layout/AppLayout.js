import logo from '../../asset/images/logo.png'
import Image from "next/image";
import { useRouter } from "next/router";
import header_search from "../../asset/images/header_search.png";
const AppLayout = ({ children }) => {
  const router = useRouter();

  return (
    <>
      <div className="header">
        <div className="header_wrap">
          <Image src={logo} alt="yehLogo" className="heaeder_logo" />
          <div className="header_search">
            <button>
              <Image src={header_search} alt="search" />
            </button>
            <input
              placeholder="관심있는 내용을 검색해보세요"
              className="header_input"
            />
          </div>
        </div>
        <div className="header_signBtn">
          <button
            onClick={() => router.push("/user/signin")}
            className="header_signin"
          >
            로그인
          </button>
          <button
            onClick={() => router.push("/board/post")}
            className="header_write"
          >
            글쓰기
          </button>
          {/* <span onClick={() => router.push("/user/signup")}>회원가입</span> */}
        </div>
      </div>
      {children}
    </>
  );
};
export default AppLayout;
