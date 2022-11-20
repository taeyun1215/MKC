import logo from "../asset/images/logo.png";
import Image from "next/image";
import { useRouter } from "next/router";

const AppLayout = ({ children }) => {
  const router = useRouter();

  return (
    <>
      <div className="header">
        <Image src={logo} alt="yehLogo" />
        <span onClick={() => router.push("/user/signin")}>로그인</span>
      </div>
      {children}
    </>
  );
};
export default AppLayout;
