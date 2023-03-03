import { createGlobalStyle} from "styled-components"

export const lightTheme = {
  body: '#FFF',
  text: '#363537',
  toggleBorder: '#FFF',
  DivBg: '#f8f8f8',
  DivBorder: '1px #dedede solid',
  mainInfo : '#424242',
  addInfo : '#424242'
}

export const darkTheme = {
  body: '#292a2d',
  text: '#FAFAFA',
  toggleBorder: '#6B8096',
  DivBg: '#2c2d30',
  DivBorder : '1px #3a3b3d solid',
  mainInfo : '#a8a8a8',
  addInfo : '#94969b'
}

export const GlobalStyles = createGlobalStyle`
  body {
    background-color: ${({ theme }) => theme.body};
    color: ${({ theme }) => theme.text};
  }
  div &.getPost &.ranking  {
    background-color: ${({ theme }) => theme.DivBg};
  }
  div &.getPost &.getPostsBox_wrap &.getPostsBox {
    border : ${({ theme }) => theme.DivBorder};
  }
  &.getPost &.getPostsBox_wrap &.getPostsBox 
    &.mainInfo &.mainInfoText &.mainInfoContents {
    color : ${({ theme }) => theme.mainInfo};
  }
  &.getPost &.getPostsBox_wrap &.getPostsBox &.addInfo &.addInfo_wrap p{
    color : ${({ theme }) => theme.addInfo};
  }
  &.getPost &.getPostsBox_wrap &.getPostsBox &.addInfo &.addInfo_wrap &.addInfoIcons {
    color : ${({ theme }) => theme.addInfo};
  }
`