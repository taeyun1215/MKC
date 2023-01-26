import { configureStore, getDefaultMiddleware  } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
// import logger from 'redux-logger'
import thunk from 'redux-thunk'
import userSlice from "../reducer/user";

const persistConfig = {
  key: "root",
  storage,
};

const persistedReducer = persistReducer(persistConfig, userSlice);

const store = configureStore({
  reducer: persistedReducer,
  middleware: [thunk]
});

export default store;
// export default persistReducer(persistConfig, userSlice);
