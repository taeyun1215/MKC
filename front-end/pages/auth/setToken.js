import cookie from "react-cookies";
import { HTTP_ONLY } from "../../config/config";

export default async function setToken (props) {
    console.log(props)
    const accessToken = props.data.access_token
    const refreshToken = props.data.refresh_token

    const expires = new Date()
    expires.setMinutes(expires.getMinutes() + 1);

    if(props.success) {
        cookie.save("accessToken", accessToken, {
            httpOnly: HTTP_ONLY,
            path: "/",
            expires : expires
        });
            cookie.save("refreshToken", refreshToken, {
            httpOnly: HTTP_ONLY,
            path: "/",
        });
    } else {
        console.log('test')
    }

    return (
        <>
        </>
    )
}