import cookies from "next-cookies";
import getToken from "./auth/getToken";
import setToken from "./auth/setToken";
import { useRouter } from "next/router";

// const [userName, setuserName] = useRecoilState(nameState);

export default function main(props) {
  const router = useRouter();
  // if(props.data === null) {
  //   const alert = confirm('세션이 만료되었습니다. 다시 로그인 후 시도해 주세요')
  //   if(alert) {
  //     router.push("/user/signin");
  //   } else {
  //     router.push("/");
  //   }
  // } else setToken(props.data)
  if(props.data !== null) setToken(props.data)
  return <div></div>;
}

export async function getServerSideProps(ctx) {
  const allCookies = cookies(ctx);
  if(allCookies.refreshToken) {
    const res = await getToken(allCookies.accessToken, allCookies.refreshToken)
    const data = res.data
    return {
      props: {
        name : "main",
        data : data  
      },
    };
  } else return {
    props: {
      name : "main",
      data : null  
    },
  } 
}