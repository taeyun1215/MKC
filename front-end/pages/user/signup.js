import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import logo from "../../asset/images/logo.png";
import Image from "next/image";
import axios from "axios";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

export default function signup() {
  // 회원가입 정보 유효성 검사 및 에러 메시지 출력
  const formSchema = yup.object({
    id: yup
      .string()
      .required("아이디는 필수 입력 정보입니다")
      .matches(
        /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{1,30}$/,
        "아이디는 영문, 숫자 조합으로 가능합니다"
      ),
    email: yup
      .string()
      .required("이메일은 필수 입력 정보입니다 입력해주세요")
      .email("이메일 형식이 아닙니다."),
    password: yup
      .string()
      .required("영문, 숫자, 특수문자 포함 8자리를 입력해주세요.")
      .min(8, "최소 8자 이상 가능합니다")
      .max(16, "최대 20자 까지만 가능합니다")
      .matches(
       /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[,./;'<>?:"~!@#$%^&*()])[a-zA-Z0-9,./;'<>?:"~!@#$%^&*()]{8,20}$/,
        "영문, 숫자, 특수문자 포함 8자리를 입력해주세요."
      ),
    passwordConfirm: yup
      .string()
      .oneOf([yup.ref("password")], "비밀번호가 일치하지 않습니다."),
    name: yup.string().required("닉네임은 필수 입력 정보입니다"),
  });

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ mode: "onChange", resolver: yupResolver(formSchema) });

  // 회원가입 정보 제출
  const onSubmit = (data) => {
    console.log("data", data);
  };
  return (
    <div className="signup">
      <div className="signup_title">
        <Image src={logo} alt="yehLogo" />
        <span>조직문화의 개선과 소통을 위해 지금 시작해보세요</span>
      </div>
      <form onSubmit={handleSubmit(onSubmit)} className="signup_contents">
        <label htmlFor="id">아이디</label>
        <input
          name="id"
          type="text"
          {...register("id")}
          placeholder="아이디를 입력해주세요"
          autoComplete="off"
        />
        {errors.id && <p>{errors.id.message}</p>}
        <label htmlFor="password">비밀번호</label>
        <input
          name="password"
          type="password"
          {...register("password")}
          placeholder="비밀번호를 입력해주세요 (8 ~ 20자의 영문, 숫자, 특수문자 조합)"
        />
        {errors.password && <p>{errors.password.message}</p>}
        <label htmlFor="passwordConfirm">비밀번호 확인</label>
        <input
          name="passwordConfirm"
          type="password"
          {...register("passwordConfirm")}
          placeholder="비밀번호를 한 번 더 입력해주세요"
        />
        {errors.passwordConfirm && <p>{errors.passwordConfirm.message}</p>}
        <label htmlFor="name">닉네임</label>
        <input
          name="name"
          type="text"
          {...register("name")}
          placeholder="닉네임을 입력해주세요"
          autoComplete="off"
        />
        {errors.name && <p>{errors.name.message}</p>}
        <label htmlFor="email">이메일</label>
        <input
          name="email"
          type="email"
          placeholder="이메일을 입력해주세요"
          {...register("email")}
          autoComplete="off"
        />
        {/* <button>인증하기</button> */}
        {errors.email && <p>{errors.email.message}</p>}
        <button type="submit" className="signup_Btn">
          가입하기
        </button>
      </form>
    </div>
  );
}
