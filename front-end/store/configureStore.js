import { configureStore, applyMiddleware, compose } from "redux";
import { createWrapper } from "next-redux-wrapper";
import { createLogger } from "redux-logger";
import { composeWithDevTools } from "redux-devtools-extension";
import rootReducer from "../reducer";

const createStore = () => {
  const logger = createLogger();
  const enhancer = compose(composeWithDevTools(applyMiddleware(logger)));
  const store = configureStore(rootReducer, enhancer);
  return store;
};

const wrapper = createWrapper(createStore, { debug: true });

export default wrapper;
