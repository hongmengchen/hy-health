package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author 李柯嘉
 *
 * Doctor类代表了一个医生对象，继承了SuperDomain类
 * 属性有医师id，医师姓名，年龄，性别，等级，手机号以及诊治级别，此外还有医师对应的账号id，
 * 创建时间以及更新时间，在对应的属性上加入了注解判断属性是否符合对应格式
 */
@Data
public class Doctor extends SuperDomain {
    private Long id;//医师id
    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "(^[\u2E80-\u9FFF]{2,4})", message = "姓名只能为汉字,且长度为2-4个汉字")
    private String name;//医师姓名
    @Range(min = 1, max = 130, message = "年龄格式错误")
    @NotNull(message = "年龄不能为空")
    private Integer age;//年龄
    @Range(min = 1, max = 2, message = "性别错误，1代表男，2代表女")
    @NotNull(message = "性别不能为空")
    private Integer sex;//性别：1男，2女
    @NotNull(message = "等级不能为空")
    private Integer levelId;//等级：1主任，2普通
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号格式不正确")
    private String phoneNumber;//电话号
    @NotNull(message = "级别不能为空")
    private Long typeId;//诊治类别id
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Long accountId;//账号id
}
