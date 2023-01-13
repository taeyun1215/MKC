import axios from "axios";
import { useEffect } from "react"
import cookie from "react-cookies";

export default function main() {
    useEffect(() => {
    const token = cookie.load("accessToken");
    console.log(token)
    try {
      axios.get('http://130.162.159.231:8080/api/token/refresh', {
        headers: {
          "Authorization" : `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }).then((res) => console.log(res))
    } 
    catch(e) {
      console.log(e)
    }
  },[])
  return (
    <div></div>
  )
  
}
