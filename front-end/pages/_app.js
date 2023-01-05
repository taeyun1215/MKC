import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/AppLayout";
import { useRouter } from "next/router";

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
export default MyApp
// export default wrapper.withRedux(MyApp);
