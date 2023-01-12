// import { configureStore, applyMiddleware, compose } from "redux";
// import { createWrapper } from "next-redux-wrapper";
// import { createLogger } from "redux-logger";
// import { composeWithDevTools } from "redux-devtools-extension";
// import rootReducer from "../reducer";

// const createStore = () => {
//   const logger = createLogger();
//   const enhancer = compose(composeWithDevTools(applyMiddleware(logger)));
//   const store = configureStore(rootReducer, enhancer);
//   return store;
// };

// const wrapper = createWrapper(createStore, { debug: true });

// export default wrapper;

// import { applyMiddleware, configureStore, compose } from "redux";
// import createSagaMiddleware from "redux-saga";
// import { createWrapper } from "next-redux-wrapper";
// import { composeWithDevTools } from "redux-devtools-extension";

// import reducer from "../reducers";
// import rootSaga from "../sagas";

// const createStore = (context) => {
//   console.log(context);
//   const sagaMiddleware = createSagaMiddleware();
//   const middlewares = [sagaMiddleware];
//   const enhancer =
//     process.env.NODE_ENV === "production"
//       ? compose(applyMiddleware(...middlewares))
//       : composeWithDevTools(applyMiddleware(...middlewares));
//   const store = configureStore(reducer, enhancer);
//   store.sagaTask = sagaMiddleware.run(rootSaga);
//   return store;
// };

// const wrapper = createWrapper(createStore, {
//   debug: process.env.NODE_ENV === "development",
// });

// export default wrapper;