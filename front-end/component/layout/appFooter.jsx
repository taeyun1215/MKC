import Image from "next/image";
import logo from "../../asset/images/logo.png";
import { FaGithub } from 'react-icons/fa';
import { useRouter } from "next/router";


export default function AppFooter() {
    const router = useRouter();

    return (
        <div className="footer">
            <div className="footer_image">
                <Image src={logo} alt="yehLogo" className="heaeder_logo" width={100}/>
                <FaGithub onClick={() => window.open('https://github.com/taeyun1215/MKC')} className='footer_icon'/>
            </div>
            <div className="footer_text">
                <p>서울특별시 강남구 언주로 535 (주)골든플래닛 DXED x SF | 권구현 • 오세은 • 이태윤 | YEH ⓒ 2023 All rights reserved.</p>
                <p>버그 문의 : ghkwon@goldenplanet.co.kr | seo@goldenplanet.co.kr | tylee@goldenplanet.co.kr</p>
            </div>
        </div>
    )
}