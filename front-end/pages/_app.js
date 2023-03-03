import "../styles/main.scss";
import Head from "next/head";
import axios from "axios";
import dynamic from 'next/dynamic'
import { useState } from "react";
import { RecoilRoot } from 'recoil';

import ThemeToggle from "../component/utils/themeToggle";
import { ThemeProvider } from "styled-components";
import { lightTheme, darkTheme, GlobalStyles } from "../component/utils/themeConfig" 

const AppLayout = dynamic(() => import('../component/layout/AppLayout'), { ssr: false })

function MyApp({ Component, pageProps}) {
  axios.defaults.baseURL = "http://43.201.144.113:8080";
  axios.defaults.withCredentials = true;
  const [theme, setTheme] = useState("light") 
  const toggleTheme = () => {
    theme == 'light' ? setTheme('dark') : setTheme('light')
}
  function PageRouter() {
    const pages = pageProps.name;
    switch(pages) {
      case 'signin' : return (
          <Component {...pageProps} />
      )
      case 'signup' : return (
          <Component {...pageProps} />
      )   
      default : return (
        <AppLayout>
          <Component {...pageProps} />
          {/* <ThemeToggle/> */}
        </AppLayout> 
      )
    }
  }
  return (
    <>
      <Head>
          <title>YEH</title>
      </Head>
      <ThemeProvider theme={theme == 'light' ? lightTheme : darkTheme}>
        <RecoilRoot >
          <GlobalStyles />
          <button onClick={toggleTheme}>Switch Theme</button>
          {PageRouter()}
        </RecoilRoot>
      </ThemeProvider>
    </>
    
  );
}
export default MyApp;
