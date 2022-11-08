import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import axios from 'axios';
import Image from "next/image";
import logo from '../../asset/images/logo.png'

export default function join() {
    const {register, handleSubmit} = useForm();

    return (
        <div className="join">
            <div className="join_title">
                <Image src={logo}/>
            </div>
            <div className="join_contents">
                <form>
                    <label htmlFor="email">이메일</label>
                    <input
                    id="email"
                    type="email"
                    placeholder="test@email.com"
                    {...register("email")}
                    />
                </form>
            </div>

        </div>
    )
}