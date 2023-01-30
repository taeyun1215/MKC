import { useS3Upload } from "next-s3-upload";

export default function Post({props}) {
  console.log(props)
  let { uploadToS3 } = useS3Upload();

  let handleFileChange = async (event) => {
    let file = event.target.files[0];
    let { url } = await uploadToS3(file);

    console.log("Successfully uploaded to S3!", url);
  };
  // https://velog.io/@hjkdw95/next-s3-upload%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%98%EC%97%AC-%EB%8B%A4%EC%A4%91-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0

  return (
    <div className="post">
      <input
        type="text"
        placeholder="제목을 입력해 주세요"
        name="title"
        autoComplete="off"
      />
      <textarea placeholder="내용을 입력해 주세요" />
      <input type="file" onChange={handleFileChange} />
      <div className="postBtn">
        <button className="cancle">취소</button>
        <button>등록</button>
      </div>
    
    </div>
  );
}
