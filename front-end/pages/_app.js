import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/layout/AppLayout";
import { useRouter } from "next/router";
import axios from "axios";
import store from "../store/index";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";
import cookies from "next-cookies";
import { Cookies } from "react-cookie";

function MyApp({ Component, pageProps}) {
  const router = useRouter();
  const persistor = persistStore(store);
  axios.defaults.baseURL = "http://130.162.159.231:8080";
  axios.defaults.withCredentials = true;
  
  return (
    <>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          <Head>
            <title>YEH</title>
          </Head>
          {router.pathname == "/user/signin" ||
          router.pathname === "/user/signup" ||
          router.pathname === "/user/signupComplete" ||
          router.pathname === "/user/authComplete" ? (
            <Component {...pageProps} />
          ) : (
            <AppLayout>
              <Component {...pageProps} />
            </AppLayout>
          )}
        </PersistGate>
      </Provider>
    </>
  );
}

export default MyApp;

// export async function getServerSideProps(ctx) {

//   const cookie = new Cookies();
//   const accessToken = cookie.get("accessToken");
 
//   const res = await axios({
//       method: "get",
//       url: "/api/token/refresh",
//       headers: {
//         Authorization: `Bearer ${accessToken}`,
//       },
//     })
//     const data = await res.json()
//     const test = 'test'
//     return { props : test };
// }


// export const getServerSideProps = async (ctx) => {
//   const cookie = new Cookies();
//   const accessToken = cookie.get("accessToken");
//   // axios({
//   //   method: "get",
//   //   url: "/api/token/refresh",
//   //   headers: {
//   //     Authorization: `Bearer ${accessToken}`,
//   //   }})
//   const test = 'test'
//   console.log(test)
//   return {
//     props: {data : accessToken, test : test}
//   }
// }
// MyApp.getInitialProps = async (ctx) => {
//   console.log("ctx" , ctx)
//   console.log("Cookie ctx" , cookies(ctx))
//   const cookie = new Cookies();
//   const accessToken = cookie.get("accessToken");
 
//   try {
//     axios({
//       method: "get",
//       url: "/api/token/refresh",
//       headers: {
//         Authorization: `Bearer ${accessToken}`,
//       },
//     }).then((res) => console.log(res.data));
//   } catch(e) {
//     console.log("error", e)
//   }
//    return {};
// };
