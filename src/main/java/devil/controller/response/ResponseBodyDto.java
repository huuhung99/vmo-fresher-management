package devil.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import devil.common.constant.ResultPage;
import devil.common.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBodyDto<E> {
	private ResponseCode code;
	private String message;
	@JsonProperty("total_items")
	private long totalItems;
	private int page;
	private int size;
	private E item;
	private List<E> items;

	public ResponseBodyDto(Pageable pageable, Page<E> page, ResponseCode code, String message) {
		this.code = code;
		this.message = message;
		this.totalItems = page.getTotalElements();
		this.page = pageable.getPageNumber();
		this.size = pageable.getPageSize();
		this.items = page.getContent();
	}

	public ResponseBodyDto(E item,ResponseCode code,String message){
		this.code=code;
		this.message=message;
		this.item=item;
	}

	public ResponseBodyDto(List<E> items,ResponseCode code,String message,int totalItems){
		this.code=code;
		this.message=message;
		this.items=items;
		this.totalItems =totalItems ;
	}

	public ResponseBodyDto(ResponseCode code,String message){
		this.code=code;
		this.message=message;
	}

	public ResponseBodyDto(Pageable pageable, ResultPage<E> page, ResponseCode code, String message) {
		this.code = code;
		this.message = message;
		this.totalItems = page.getTotalItems();
		this.page = pageable.getPageNumber();
		this.size = pageable.getPageSize();
		this.items = page.getPageList();
	}

	public ResponseBodyDto(ResultPage<E> page, ResponseCode code, String message) {
		this.code = code;
		this.message = message;
		this.totalItems = page.getTotalItems();
		this.items = page.getPageList();
	}

	public ResponseBodyDto(Pageable pageable, List<E> items, ResponseCode code, String message) {
		this.code = code;
		this.message = message;
		this.totalItems = items.size();
		this.page = pageable.getPageNumber();
		this.size = pageable.getPageSize();
		this.items = items;
	}
}
