package com.example.siren.domain.carImage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarImageService {
    private final CarImageRepository carImageRepository;

    private final CarFileHandler fileHandler;

    @Autowired
    public CarImageService(CarImageRepository boardRepository) {
        this.carImageRepository = boardRepository;
        this.fileHandler = new CarFileHandler();
    }

    public CarImage addBoard(CarImage ci, List<MultipartFile> files, long id) throws Exception {
        // 파일을 저장하고 그 Board 에 대한 list 를 가지고 있는다
        List<CarImage> list = fileHandler.parseFileInfo(files,id);

        if (list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
            List<CarImage> pictureBeans = new ArrayList<>();
            for (CarImage cis : list) {
                log.info("borders={}",cis.getOriginalFileName());
                pictureBeans.add(carImageRepository.save(cis));
            }
        }

        return ci;
    }

    public List<CarImage> findCis() {
        return carImageRepository.findAll();
    }

    public Optional<CarImage> findCi(Long id) {
        return carImageRepository.findById(id);
    }

    public void update(List<MultipartFile> files,long id)throws Exception  {

        CarImage findCi = carImageRepository.findById(id).orElseThrow();

        List<CarImage> list = fileHandler.parseFileInfo(files,id);

        if (list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
            for (CarImage cis : list) {
                log.info("borders={}",cis.getOriginalFileName());
                findCi.setOriginalFileName(cis.getOriginalFileName());
                findCi.setStoredFileName(cis.getStoredFileName());
                findCi.setFileSize(cis.getFileSize());
                findCi.setBoardIdx(cis.getBoardIdx());
            }
        }

    }
}
