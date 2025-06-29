package com.emr.slgi.attachment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachmentDAO {

	void insertAttachment(Attachment att);

	List<Attachment> selectAttachments(int id);
}
