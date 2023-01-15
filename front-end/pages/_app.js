import "../styles/main.scss";
import Head from "next/head";
import AppLayout from "../component/layout/AppLayout";
import { useRouter } from "next/router";
import axios from "axios";
import store from "../store/index";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";

function MyApp({ Component, pageProps }) {
  const router = useRouter();
  const persistor = persistStore(store);
  axios.defaults.baseURL = "http://130.162.159.231:8080";
  axios.defaults.withCredentials = true;

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
