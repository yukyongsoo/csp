/*package yukEcm.mount;

import java.io.InputStream;

import org.joda.time.LocalDate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import yukCommon.adaptor.StorageAdaptor;
import yukCommon.exception.NotSupportException;
import yukCommon.model.Content;
import yukCommon.model.Storage;
import yukEcm.config.BaseProperty;

public class AWSS3Adaptor extends StorageAdaptor{
	AmazonS3 s3;
    String bucketName = "";
    int maxHash = 300;
	int current = 1;
    
	@Override
	protected String addFileImpl(Content content) throws Exception {
		String loc = setLocImpl();
		loc = loc + "/" + content.docId;
		content.setLoc(loc);
		s3.putObject(new PutObjectRequest(bucketName, loc, content.getItem().getInputStream(),null));
		return loc;
	}

	@Override
	protected InputStream getFileImpl(Content content) throws Exception {
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, content.getLoc()));
		return object.getObjectContent();
	}

	@Override
	protected void deleteFileImpl(Content content) throws Exception {
		 s3.deleteObject(bucketName, content.getLoc());
	}

	@Override
	protected long setRetentionImpl(Content content, long time) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setInitOptionImpl(Storage storage) throws Exception {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(storage.wormId, storage.wormPass);
		s3 = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
        .withRegion(Regions.AP_NORTHEAST_2)
        .build();
		bucketName = storage.baseDir;
	}

	@Override
	protected String setLocImpl() throws Exception {
		LocalDate now = LocalDate.now();
		String path = BaseProperty.apNum + "/" + now.getYear() + "/" +
				now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/" + current;
		if(current++ > maxHash)
			current = 1;
		return path;
	}

	@Override
	protected long getStorageSizeImpl() throws Exception {
		throw new NotSupportException("AWS S3 Is Not Have Service Limit Size. Ignore this");
	}

	@Override
	protected void storageSizeFullHandler() throws Exception {
		throw new NotSupportException("AWS S3 Is Not Have Service Limit Size. Ignore this");
	}

}
*/