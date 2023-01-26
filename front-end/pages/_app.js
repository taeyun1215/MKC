import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/layout/AppLayout";
import { useRouter } from "next/router";
import axios from "axios";
import store from "../store/index";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";
import { useEffect } from "react";

function MyApp({ Component, pageProps }) {
  const router = useRouter();
  const persistor = persistStore(store);
  axios.defaults.baseURL = "http://130.162.159.231:8080";
  // axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  axios.defaults.withCredentials = true;

  useEffect(() => {
    console.log(axios.defaults.headers.common.Authorization)
  },[])
  // useEffect(() => {
  //   try {
  //     axios.get("/api/token/refresh").then((res) => console.log(res));
  //   } catch (e) {
  //     console.log(e);
  //   }
  // }, []);

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

// export async function getServerSideProps(context) {
//   console.log(context)
//   // const cookieString = context.req ? context.req.headers.cookie : '';

// 	// set the cookies
// 	// context.res.setHeader('set-Cookie', 'foo=bar; HttpOnly');
//   const res = axios.get("/api/token/refresh" , {headers : `Bearer ${cookieString}`})
//   const data = await res.json()
//   return { props: { data } }
// }

// export function getServerSideProps(context) {
//   // print incoming headers
//   console.log(context.req.headers);

//   // add header
//   context.res.setHeader("X-Foo", "Bar");

//   return {
//     props: {},
//   };
// }
MyApp.getInitialProps = async (ctx) => {
  console.log(axios.defaults.headers.common.Authorization)
  // console.log( axios.defaults.headers.common[
  //   "Authorization"
  // ])
  // const { ctx, Component } = context;
  // let pageProps = {title : 123123};
  // console.log(ctx?.ctx?.req?.headers?.cookie)
  axios.get("/api/token/refresh").then((res) => console.log(res));

  return { };
};