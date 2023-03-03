import {useRecoilState } from 'recoil';
import { BsSun, BsFillMoonFill } from "react-icons/bs";

import { themeState } from '../../store/states';

const ThemeToggle = () => {
  const [theme, setTheme] = useRecoilState(themeState);

  const handleThemeToggle = () => {
    if (theme === 'light') setTheme('dark')
    else setTheme('light')
  }
  
  return (
        <button onClick={() => handleThemeToggle()} className='themeToggle'>
            {theme === 'light' ? <BsSun className="themeToggleIcon"/> : <BsFillMoonFill className="themeToggleIcon"/>}
        </button>
    )
}
export default ThemeToggle