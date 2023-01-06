import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/AppLayout";
import { useRouter } from "next/router";
import { stayLogged } from "./user/authToken";

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
    await stayLogged(context);
  }
);


export default MyApp
