// import { END } from "redux-saga";
// import cookie from "cookie";
// // import { loadMeRequest, setToken, refreshTokenRequest } from "../reducers/user";
// export const stayLogged = async (context) => {

//   const parsedCookies = context.req
//     ? cookie.parse(context.req.headers.cookie || "")
//     : ""; // 서버라면 쿠키 확인
//   if (parsedCookies) {
//     if (parsedCookies["accessToken"]) {
//       // 액세스 토큰이 있으면 액세스 토큰으로 사용자 정보 불러옴
//       context.store.dispatch(
//         loadMeRequest({
//           accessToken: parsedCookies["accessToken"],
//         })
//       );
//     } else if (parsedCookies["refreshToken"]) {
//       // refresh 토큰만 있으면 토큰 재발급 및 사용자 정보 재요청
//       context.store.dispatch(
//         refreshTokenRequest({
//           refreshToken: parsedCookies["refreshToken"],
//         })
//       );
//     }
//   }
//   context.store.dispatch(END);
//   await context.store.sagaTask.toPromise(); // 기다림
//   const {
//     id,
//     accessToken,
//     refreshToken,
//     setCookie,
//   } = context.store.getState().user;
//   if (id) { // 사용자 정보 재요청된 경우
//     if (accessToken && refreshToken) {
//       // refresh한 경우 쿠키 다시 세팅
//       context.res.setHeader("Set-Cookie", setCookie); // setCookie는 refresh 요청 이후 리덕스에 저장해둠
//     } else {
//       // access token으로 요청한 경우 현재 토큰 리덕스에 담아둠
//       context.store.dispatch(
//         setToken({
//           accessToken: parsedCookies["accessToken"],
//           refreshToken: parsedCookies["refreshToken"],
//         })
//       );
//     }
//     context.store.dispatch(END);
//     await context.store.sagaTask.toPromise();
//   }
// };

