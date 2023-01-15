import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import userSlice from "../reducer/user";

const persistConfig = {
  key: "root",
  storage,
};

const persistedReducer = persistReducer(persistConfig, userSlice);

const store = configureStore({
  reducer: persistedReducer,
});

export default store;

// const reducers= combineReducers({
//   user: userSlice.reducer
// })

// const persistConfig = {
//   key: "root",
//   storage,
//   whitelist : ['user']
// };

// const persistedReducer  = persistReducer(persistConfig, reducers)

// const store = configureStore({
//   reducer: persistedReducer
// })
