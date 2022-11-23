package com.mck.domain.image;

import com.mck.domain.post.Post;
import com.mck.infra.image.FileService;
import com.mck.infra.image.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    // 여러 파일을 나눠서 저장하기 위한 메소드.
    @Override
    @Transactional
    public void saveImages(Post post, List<MultipartFile> imageFiles) throws IOException {
        for (MultipartFile imageFile : imageFiles) {
            saveImage(post, imageFile);
        }
    }

    // 하나씩 파일을 나눠서 저장함.
    @Transactional
    public void saveImage(Post post, MultipartFile imageFile) throws IOException {

        // fileService 에서 다 처리 해 뒀습니다.
        UploadFile uploadFile = fileService.storeFile(imageFile);
        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
        String originalFilename = uploadFile != null ? uploadFile.getOriginalFileName() : "";
        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";

        Image image = Image.builder()
                .imageName(storeFileName)
                .imageUrl(imageUrl)
                .originalImageName(originalFilename)
                .post(post)
                .build();

        imageRepo.save(image);
    }

    // 이미지 순번대로 반환해주는 JPA
    public List<Image> findByPostOrderByImageIdAsc(Post post) {
        return imageRepo.findByPostOrderByIdAsc(post);
    }

    // 이미지 업데이트
    @Transactional
    public void updateImage(List<MultipartFile> imageFiles, Post post) throws IOException {

        List<Image> oldImages = imageRepo.findByPost(post);

        // 기존 상품 이미지 파일이 존재하는 경우 파일 삭제
        for (Image oldImage : oldImages) {
            if(StringUtils.hasText(oldImage.getImageName())) {
                fileService.deleteFile(oldImage.getImageUrl()); // local 데이터 삭제
            }
        }

        // DB 데이터 삭제
        imageRepo.deleteByPost(post);

        // DB 이미지 저장
        saveImages(post, imageFiles);
    }

    // 이미지 삭제
    @Transactional
    public void deleteImage(Image image) throws IOException {
        // 기존 이미지 파일 삭제
        String fileUploadUrl = fileService.getFullFileUploadPath(image.getImageName());
        fileService.deleteFile(fileUploadUrl);

        // 이미지 정보 초기화, entity 에서 처리.
        image.initImageInfo();
    }

}
