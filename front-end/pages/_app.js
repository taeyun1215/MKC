import "../styles/main.scss";
import Head from "next/head";
import { useRouter } from "next/router";
import AppLayout from "../component/AppLayout";

function MyApp({ Component, pageProps }) {
  const router = useRouter();
  return (
    <>
      <Head>
        <title>YEH</title>
      </Head>
      {router.pathname == "/user/signin" ||
      router.pathname === "/user/signup" ? (
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
