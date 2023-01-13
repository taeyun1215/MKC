import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/layout/AppLayout";
import { useRouter } from "next/router";
import { useEffect } from "react";
import loginApi from '../component/utils/loginApi';

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

export default MyApp;
