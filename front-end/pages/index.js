// import "../styles/main.scss";
import axios from "axios";
import { Cookies } from "react-cookie";

export default function Home({props}) {
  // console.log(props)
  return <div></div>;
}

export async function getServerSideProps(ctx) {
  console.log('**************************')
  console.log(ctx.req.headers)

  const cookie = new Cookies();
  const accessToken = cookie.get("accessToken");
  // console.log(accessToken)
  const res = await axios({
      method: "get",
      url: "http://130.162.159.231:8080/api/token/refresh",
      headers: {
        Authorization: `Bearer ${ctx.req.headers.cookie}`,
      },
    })
    const data = await res.json();
    // console.log(data)
    return { props : data };
}
