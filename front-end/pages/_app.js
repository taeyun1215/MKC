import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/AppLayout";
import { useRouter } from "next/router";
import { stayLogged } from "./user/authToken";
import wrapper from "../store/configureStore";
import cookies from "next-cookies";
import App from "next/app";

function MyApp({ Component, pageProps }) {
  const router = useRouter();

  return (
    <>
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
    </>
  );
}
export const getServerSideProps = wrapper.getServerSideProps(
  async (context) => {
    const parsedCookie = context.req
      ? cookie.parse(context.req.headers.cookie || "")
      : "";
    console.log(parsedCookie);
    if (context.req && parsedCookie) {
      if (parsedCookie["accessToken"]) {
        context.store.dispatch({
          type: LOAD_MY_INFO_REQUEST,
          data: parsedCookie["accessToken"],
        });
      }
    }
    context.store.dispatch(END);
    await context.store.sagaTask.toPromise();
  }
);

// export const getServerSideProps = wrapper.getServerSideProps(
//   async (context) => {
//     await stayLogged(context);
//   }
// );

export default MyApp;
