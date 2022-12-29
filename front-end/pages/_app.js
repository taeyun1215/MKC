import "../styles/main.scss";
import Head from "next/head";
import { useRouter } from "next/router";
import AppLayout from "../component/AppLayout";
import { QueryClient, QueryClientProvider, Hydrate } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";

const client = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

function MyApp({ Component, pageProps }) {
  const router = useRouter();
  return (
    <>
      <QueryClientProvider client={client}>
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
      </QueryClientProvider>
    </>
  );
}

export default MyApp;
