import "../styles/main.scss";
import Head from "next/head";
import { useRouter } from "next/router";
import AppLayout from "../component/AppLayout";
import { Provider } from "react-redux";

function MyApp({ Component, pageProps, store }) {
  const router = useRouter();
  return (
    <>
      <Head>
        <title>YEH</title>
      </Head>
      {router.pathname == "/user/signin" ||
      router.pathname === "/user/signup" ||
      router.pathname === "/user/signupComplete" ? (
        // <Provider store={store}>
        <Component {...pageProps} />
      ) : (
        // </Provider>
        <AppLayout>
          <Component {...pageProps} />
        </AppLayout>
      )}
    </>
  );
}

export default MyApp;
