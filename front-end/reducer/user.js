import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  IsLog: false,
  Nickname: "",
  EmailAuth: false,
};

export const userSlice = createSlice({
  name: "userInfo",
  initialState,
  reducers: {
    isLog: (state, action) => {
      state.IsLog = action.payload;
    },
    nickname: (state, action) => {
      state.Nickname = action.payload;
    },
    emailAuth: (state, action) => {
      state.EmailAuth = action.payload;
    },
  },
});

export const { nickname, emailAuth, isLog } = userSlice.actions;

export default userSlice.reducer;
