import logo from "../../asset/images/logo.png";
import Image from "next/image";
import { useRouter } from "next/router";
import header_search from "../../asset/images/header_search.png";
import { Dropdown, Button } from 'antd';
import {  useRecoilState } from "recoil";
import { nameState , logginState} from "../../store/states";
import cookie from "react-cookies";

const AppLayout = ({ children }) => {
  const router = useRouter();
  const [userName, setuserName] = useRecoilState(nameState);
  const [IsLoggin, setIsLoggin] = useRecoilState(logginState);
  
  const logout = () => {
    setIsLoggin(false);
    setuserName(null)
    cookie.remove('accessToken');
    cookie.remove('refreshToken');
  }

 const items = [
   {
     key: '1',
     label: (
      <a>
        마이페이지
      </a>
    )
   },
   {
     key: '2',
     label: (
       <a onClick={() => logout()}>로그아웃</a>
     ),
   },
  
 ];
  return (
    <>
      <div className="header">
        <div className="header_wrap">
          <Image src={logo} alt="yehLogo" className="heaeder_logo" onClick={() => router.push('/')}/>
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
          {IsLoggin ? (
           <Dropdown menu={{ items }} placement="bottom">
           <Button>{userName}</Button>
         </Dropdown>
          ) : (
          <div>
            <button
              onClick={() => router.push("/user/signin")}
              className="header_signin"
            >
              로그인
            </button>
            <span style={{color:"#2b3089"}}> | </span>
            <button
              onClick={() => router.push("/user/signup")}
              className="header_signin"
            >
              회원가입
            </button>
            </div>
            )} 
           <button
            onClick={() => router.push("/board/post")}
            className="header_write"
          >
            글쓰기
          </button>
        </div>
      </div>
      {children}
    </>
  );
};
export default AppLayout;
