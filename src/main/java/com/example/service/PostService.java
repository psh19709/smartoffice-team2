package com.example.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.dto.post.CommentDto;
import com.example.dto.post.PostListDtoWithMyComment;
import com.example.dto.post.PostListDtoWithMyScrap;
import com.example.dto.post.PostListDtoWithNotice;
import com.example.dto.post.PostDetailDto;
import com.example.dto.post.PostListDto;
import com.example.mapper.PostMapper;
import com.example.utils.Pagination;
import com.example.vo.post.AttachedFile;
import com.example.vo.post.Comment;
import com.example.vo.post.Post;
import com.example.vo.post.Tag;
import com.example.web.request.PostModifyForm;
import com.example.web.request.PostRegisterForm;
import com.example.web.request.PostSearchOption;
import com.example.web.response.PostSearchResult;

@Service
public class PostService {

	@Autowired
	PostMapper postMapper;
	@Value("${file.directory}")
	private String directory;
	
	public PostSearchResult getPosts(int page, PostSearchOption opt) {
		int totalRows = postMapper.getTotalRows(opt);
		Pagination pagination = new Pagination(page, totalRows, opt.getRows());
		
		List<PostListDto> posts = postMapper.getPostListDto(pagination.getBegin(), pagination.getEnd(), opt);
		
		PostSearchResult result = new PostSearchResult(pagination, posts);
		
		return result;
	}
	
	public PostDetailDto getPostDetailDto(int postNo, int employeeNo) {
		PostDetailDto postDetailDto = postMapper.getPostDetailDto(postNo, employeeNo);
		List<AttachedFile> attachedFiles = postMapper.getAttachedFilesByPostNo(postNo);
		List<Tag> tags = postMapper.getTagsByPostNo(postNo);
		
		if (!attachedFiles.isEmpty()) {
			Map<String, String> fileNamesMap = new HashMap<String, String>();
			for (AttachedFile file : attachedFiles) {
				fileNamesMap.put(file.getSavedName(), file.getOriginalName());
			}
			postDetailDto.setFileNamesMap(fileNamesMap);
		}
		
		if (!tags.isEmpty()) {
			List<String> tagContents = new ArrayList<String>();
			for (Tag tag : tags) {
				tagContents.add(tag.getContent());
			}
			postDetailDto.setTagContents(tagContents);
		}
		
		return postDetailDto;
	}
	
	
	public void registerPost(int employeeNo, PostRegisterForm form) { 
		int postNo = postMapper.getPostSequence();
		
		// POST 테이블에 게시글 정보 저장
		Post post = Post.builder()
					.no(postNo)
					.employeeNo(employeeNo)
					.title(form.getTitle())
					.content(form.getContent())
					.build();
		postMapper.insertPost(post);
		
		// POST_TAGS 테이블에 태그 정보 저장
		if (form.getTagContents() != null) {
			List<String> tagContents = form.getTagContents();
			List<Tag> tags = new ArrayList<Tag>();
			
			for (String tagContent : tagContents) {
				Tag tag = new Tag(postNo, tagContent);
				tags.add(tag);
			}
			postMapper.insertTags(tags);
		}
		
		// POST_ATTACHED_FILES 테이블에 업로드파일 정보 저장. 첨부파일 유무와 상관없이 컨트롤러에서 이미 fileNamesMap 생성한 후, form에 대입하기 때문에 isEmpty()로 체크해야 한다.
		if (!form.getFileNamesMap().isEmpty()) {
			Map<String, String> fileNamesMap = form.getFileNamesMap();
			List<AttachedFile> attachedFiles = new ArrayList<AttachedFile>();
			
			for (Map.Entry<String, String> entry : fileNamesMap.entrySet()) {
				AttachedFile attachedFile = new AttachedFile(postNo, entry.getKey(), entry.getValue());
				attachedFiles.add(attachedFile);
			}
			postMapper.insertAttachedFiles(attachedFiles);
		}
	}

	public void scrapPost(int postNo, int employeeNo) {
		PostDetailDto postDetailDto = postMapper.getPostDetailDto(postNo, employeeNo);
		Post post = postMapper.getPostByNo(postNo);
		
		if (!postDetailDto.isScrapped()) {
			postMapper.insertPostScrap(postNo, employeeNo);
			post.increaseScrapCount();
			postMapper.updatePost(post);
		} else {
			postMapper.deletePostScrap(postNo, employeeNo);
			post.decreaseScrapCount();
			postMapper.updatePost(post);
		}
		
	}
	
	public void recommendPost(int postNo, int employeeNo) {
		PostDetailDto postDetailDto = postMapper.getPostDetailDto(postNo, employeeNo);
		Post post = postMapper.getPostByNo(postNo);
		
		if (!postDetailDto.isRecommended()) {
			postMapper.insertPostRecommend(postNo, employeeNo);
			post.increaseRecommendCount();
			postMapper.updatePost(post);
		} else {
			postMapper.deletePostRecommend(postNo, employeeNo);
			post.decreaseRecommendCount();
			postMapper.updatePost(post);
		}
	}
	
	public void registerComment(int postNo, String content, int employeeNo) {
		int commentNo = postMapper.getCommentSequence();
		Comment comment = Comment.builder()
						  .no(commentNo)
						  .employeeNo(employeeNo)
						  .postNo(postNo)
						  .content(content)
						  .build();
		postMapper.insertComment(comment);
		
		Post post = postMapper.getPostByNo(postNo);
		post.increaseCommentCount();
		postMapper.updatePost(post);
	}
	
	public void modifyComment(int commentNo, String modifiedContent) {
		Comment comment = postMapper.getCommentByNo(commentNo);
		comment.modifyContent(modifiedContent);
		postMapper.updateComment(comment);
	}
	
	public void deleteComment(int postNo, int commentNo) {
		postMapper.deleteComment(commentNo);
		
		Post post = postMapper.getPostByNo(postNo);
		post.decreaseCommentCount();
		postMapper.updatePost(post);
	}
	
	public List<CommentDto> getComments(int postNo, int employeeNo) {
		List<CommentDto> comments = postMapper.getCommentsByPostNo(postNo, employeeNo);
		return comments;
	}
	
	public void recommendComment(int commentNo, int employeeNo) {
		CommentDto commentDto = postMapper.getCommentDtoByCommentNo(commentNo, employeeNo);
		Comment comment = postMapper.getCommentByNo(commentNo);
		
		if(!commentDto.isRecommended()) {
			postMapper.insertCommentRecommend(commentNo, employeeNo);
			comment.increaseRecommendCount();
			postMapper.updateComment(comment);
		} else {
			postMapper.deleteCommentRecommend(commentNo, employeeNo);
			comment.decreaseRecommendCount();
			postMapper.updateComment(comment);
		}
	}

	public void recoverPost(List<Integer> postNoList) {
		List<Post> posts = postMapper.getPostsByNoList(postNoList);
		for (Post post : posts) {
			post.recoverPost();
		}
		postMapper.updatePosts(posts);
	}
	
	public void removePost(int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		post.removePost();
		postMapper.updatePost(post);
	}
	
	public void modifyPost(PostModifyForm form) {
		Post modifyPost = postMapper.getPostByNo(form.getNo());
		
		// 글 제목, 내용 수정
		modifyPost.modifyTitle(form.getTitle());
		modifyPost.modifyContent(form.getContent());
		postMapper.updatePost(modifyPost);
		
		// 원래 게시글에 저장된 태그 정보 삭제
		if (form.getDeleteTagContents() != null) {
			List<String> deleteTagContents = form.getDeleteTagContents();
			
			postMapper.deleteTags(deleteTagContents);
		}
		
		// 새 태그 저장.
		if (form.getTagContents() != null) {
			List<String> tagContents = form.getTagContents();
			List<Tag> tags = new ArrayList<Tag>();
			
			for (String tagContent : tagContents) {
				Tag tag = new Tag(modifyPost.getNo(), tagContent);
				tags.add(tag);
			}
			postMapper.insertTags(tags);
		}

		// 원래 게시글에 업로드되어 있던 파일 삭제
		if (form.getDeleteFileNames() != null) {
			List<String> deleteFilenames = form.getDeleteFileNames();
			
			for (String filename : deleteFilenames) {
				File file = new File(directory, filename);
				if (file.exists()) {
					file.delete();
				}
			}
			postMapper.deleteFiles(deleteFilenames);
		}
		
		// 새로 업로드한 파일 정보 저장
		if (!form.getFileNamesMap().isEmpty()) {
			Map<String, String> fileNamesMap = form.getFileNamesMap();
			List<AttachedFile> attachedFiles = new ArrayList<AttachedFile>();
			
			for (Map.Entry<String, String> entry : fileNamesMap.entrySet()) {
				AttachedFile attachedFile = new AttachedFile(modifyPost.getNo(), entry.getKey(), entry.getValue());
				attachedFiles.add(attachedFile);
			}
			postMapper.insertAttachedFiles(attachedFiles);
		}		
		
	}
	
	public void deletePost(List<Integer> postNoList) {
		/*
		List<List<AttachedFile>> attachedFilesList = postMapper.getAttachedFilesListByPostNoList(postNoList);
		for (List<AttachedFile> attachedFiles : attachedFilesList) {
			for (AttachedFile attachedfile : attachedFiles) {
				String fileName = attachedfile.getSavedName();
				File file = new File(directory, fileName);
				if (!file.exists()) {
					file.delete();
				}
			}
		}
		 */
		for (int postNo : postNoList) {
			List<AttachedFile> files = postMapper.getAttachedFilesByPostNo(postNo);
			for (AttachedFile attachedFile : files) {
				String fileName = attachedFile.getSavedName();
				File file = new File(directory, fileName);
				if (file.exists()) {
					file.delete();
				}
				
			}
		}
		
		postMapper.deletePosts(postNoList);
	}
	
	public PostSearchResult getPostsWithMyComment(int page, PostSearchOption opt) {
		int totalRows = postMapper.getTotalRowsWithMyComment(opt);
		Pagination pagination = new Pagination(page, totalRows, opt.getRows());
		
		List<PostListDtoWithMyComment> posts = postMapper.getPostListDtoWithMyComment(pagination.getBegin(), pagination.getEnd(), opt);
		
		PostSearchResult result = new PostSearchResult(pagination, posts);
		
		return result;
	}
	
	public PostSearchResult getPostsWithMyScrap(int page, PostSearchOption opt) {
		int totalRows = postMapper.getTotalRowsWithMyScrap(opt);
		Pagination pagination = new Pagination(page, totalRows, opt.getRows());
		
		List<PostListDtoWithMyScrap> posts = postMapper.getPostListDtoWithMyScrap(pagination.getBegin(), pagination.getEnd(), opt);
		
		PostSearchResult result = new PostSearchResult(pagination, posts);
		
		return result;
	}
	
	public PostSearchResult getPostsWtihNotice(int page, PostSearchOption opt) {
		int totalRows = postMapper.getTotalRowsWithNotice(opt);
		Pagination pagination = new Pagination(page, totalRows, opt.getRows());
		
		List<PostListDtoWithNotice> posts = postMapper.getPostListDtoWithNotice(pagination.getBegin(), pagination.getEnd(), opt);
		
		PostSearchResult result = new PostSearchResult(pagination, posts);
		
		return result;
	}
	
	public List<String> getEmployeeRoles(int employeeNo) {
		List<String> employeeRoles = postMapper.getEmpRolesByEmployeeNo(employeeNo);
		return employeeRoles;
	}
}
