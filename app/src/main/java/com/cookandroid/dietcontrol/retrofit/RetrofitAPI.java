package com.cookandroid.dietcontrol.retrofit;

import com.cookandroid.dietcontrol.model.CouponVO;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.DietLogVO;
import com.cookandroid.dietcontrol.model.EventVO;
import com.cookandroid.dietcontrol.model.ExerciseLogVO;
import com.cookandroid.dietcontrol.model.OrderList2VO;
import com.cookandroid.dietcontrol.model.OrderListVO;
import com.cookandroid.dietcontrol.model.OrderVO;
import com.cookandroid.dietcontrol.model.PdJoinReviewVO;
import com.cookandroid.dietcontrol.model.PointGarlicVO;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.model.ReviewVO;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitAPI {
    //조회=> GET, 전송(서버가 바뀌어야할때)=>POST

    //(로그인 된)세션에 있는 회원 번호를 반환
    @GET("customer/login_session.php")
    Call<String> callGetSession(
    );

    //회원 번호로 회원객체를 반환
    @GET("customer/select_customer_info.php")
    Call<CustomerVO> callSelectCustomerInfo(
            @Query("c_seq") String c_seq
    );

    //회원 아이디와 패스워드를 통해 회원 번호를 반환
    @GET("customer/login.php")
    Call<String> callLogin(
            @Query("c_id") String c_id,
            @Query("c_pw") String c_pw
    );

    //회원아이디의 중복 여부를 반환
    @GET("customer/check_exist_ID.php")
    Call<Boolean> callCheckExistID(
            @Query("c_id") String c_id
    );

    //회원객체를 통해 회원가입
    @FormUrlEncoded
    @POST("customer/insert_customer.php")
    Call<Void> callInsertCustomer(
            @Field("c_id") String c_id,
            @Field("c_pw") String c_pw,
            @Field("c_name") String c_name,
            @Field("c_alias")String c_alias,
            @Field("c_tell")String c_seq,
            @Field("c_birth")String c_birth,
            @Field("c_email")String c_email,
            @Field("c_gender")String c_gender,
            @Field("c_agree_email") Boolean c_agree_email,
            @Field("c_agree_sms") Boolean c_agree_sms
    );
    /**
     *  제품 및 주문
     */
    // 1. 기본순
    @GET("product/SelectProductByCate.php")
    Call<ProductVO[]> callSelectProductByCate(
            @Query("p_category") String p_category
    );

    // 2. 가격낮은순
    @GET("product/SelectProductByCateAsc.php")
    Call<ProductVO[]> callSelectProductByCateAsc(
            @Query("p_category") String p_category
    );

    // 3.  가격높은순
    @GET("product/SelectProductByCateDesc.php")
    Call<ProductVO[]> callSelectProductByCateDesc(
            @Query("p_category") String p_category
    );

    // 회원번호 쿠폰 목록 조회
    @GET("product/SelectCouponById.php")
    Call<CouponVO[]> callSelectCouponById(
            @Query("c_seq") int c_seq
    );

    // 상품번호로 상품 조회
    @GET("product/SelectProductById.php")
    Call<ProductVO> callSelectProductById(
            @Query("p_seq") int p_seq
    );

    // 상품 구매
    @FormUrlEncoded
    @POST("product/OrderProduct.php")
    Call<Void> callOrderProduct(
            @Field("c_seq") int c_seq,
            @Field("p_seq") int p_seq,
            @Field("o_cnt") int o_cnt,
            @Field("o_person") String o_person,
            @Field("o_zipcode") String o_zipcode,
            @Field("o_address") String o_address,
            @Field("o_subaddress") String o_subaddress,
            @Field("o_tell") String o_tell,
            @Field("o_price") int o_price,
            @Field("o_pay") String o_pay,
            @Field("o_card") String o_card,
            @Field("o_point") int o_point,
            @Field("o_coupon_name") String o_coupon_name
    );

    // 제품 구매 후 제품 재고 수정
    @FormUrlEncoded
    @POST("product/UpdateProductByPSEQ.php")
    Call<Void> callUpdateProductByPSEQ(
            @Query("p_seq") int p_seq,
            @Field("p_stock") int p_stock
    );

    // 주문번호로 주문 조회
    @GET("product/SelectOrderById.php")
    Call<OrderVO> callSelectOrderById(
            @Query("o_seq") int o_seq
    );



    // 회원번호와 상품번호로 구매내역 유무 조회
    @GET("review/check_purchase_product.php")
    Call<Boolean> callCheckPurchaseProduct(
            @Query("c_seq") int c_seq,
            @Query("p_seq") int p_seq
    );

    // 리뷰 작성
    @FormUrlEncoded
    @POST("review/InsertReview.php")
    Call<Void> callInsertReview(
            @Field("o_seq") int o_seq,
            @Field("c_seq") int c_seq,
            @Field("p_seq") int p_seq,
            @Field("re_contents") String re_contents,
            @Field("re_img") String re_img,
            @Field("re_rating") int re_rating
    );

    // 회원번호, 상품번호로 오더번호 조회
    @GET("review/SelectOrderBySEQ.php")
    Call<OrderVO[]> callSelectOrderBySEQ(
            @Query("c_seq") int c_seq,
            @Query("p_seq") int p_seq
    );

    // customer 모두 조회
    @GET("review/SelectCustomer.php")
    Call<CustomerVO[]> callSelectCustomer(
    );

    // 리뷰 전체 조회(별점순)
    @GET("review/SelectReviewRating.php")
    Call<ReviewVO[]> callSelectReviewRating(
            @Query("p_seq") int p_seq
    );

    // 리뷰 전체 조회(별점순)
    @GET("review/SelectReviewDate.php")
    Call<ReviewVO[]> callSelectReviewDate(
            @Query("p_seq") int p_seq
    );

    // 리뷰 개수 조회
    @GET("review/SelectCountReview.php")
    Call<Integer> callSelectCountReview(
            @Query("p_seq") int p_seq
    );







    /**
     * 이벤트 공지
     *
     */
    @GET("event_notice/select_event_notice.php")
    Call<EventVO[]> callSelectEventNoticeList(
    );


    /**
     * 마이 페이지
     */
    //비밀번호 변경
    @FormUrlEncoded
    @POST("mypage/password_change.php")
    Call<Void> callUpdatePassword(
            @Field("c_id") String c_id,
            @Field("c_pw") String c_pw
    );
    //주소 수정
    @FormUrlEncoded
    @POST("mypage/update_myInfo.php")
    Call<Void> callUpdateMyInfo(
            @Field("c_id") String c_id,
            @Field("c_alias") String c_alias,
            @Field("c_tell") String c_tell,
            @Field("c_birth") String c_birth,
            @Field("c_email") String c_email,
            @Field("c_gender") String c_gender,
            @Field("ad_zipcode") String ad_zipcode,
            @Field("ad_address") String ad_address,
            @Field("ad_subaddress") String ad_subaddress,
            @Field("c_agree_email") Boolean c_agree_email,
            @Field("c_agree_sms") Boolean c_agree_sms
    );

    //로그인 세션2 -> 회원번호 반환
    @GET("customer/login_from_app.php")
    Call<String> callLoginFromApp(
        @Query("c_seq") String c_seq
    );


    //로그아웃 세션 -> 성공시 true 반환
    @GET("customer/login_session_unset.php")
    Call<Boolean> callLogout();





    //회원 포인트 증가
        @FormUrlEncoded
        @POST("diet_log/update_plus_customer_point.php")
            Call<Boolean> callUpdatePlusCustomerPoint(
            @Field("c_seq")String c_seq,
            @Field("pt_name")String pt_name,
            @Field("pt_point")int c_point

    );

    //포인트 리스트 조회

    //쿠폰 리스트 조회
    @GET("mypage/select_coupon_list.php")
    Call<CouponVO[]> callSelectCoupon(
            @Query("c_seq") String c_seq
    );
    //내가쓴글 리스트 조회(시간역순)
    @GET("mypage/select_review_list.php")
    Call<ReviewVO[]> callSelectReview(
            @Query("c_seq") String c_seq
    );




    /**
     * 다이어트 일기
     */
    //회원 번호와 날짜로 다이어트로그 객체 리스트 반환
    @GET("diet_log/select_diet_log.php")
    Call<DietLogVO[]> callSelectDietLog(
            @Query("c_seq") String c_seq,
            @Query("d_date") String d_date
    );

    //회원 번호와 날짜로 운동 로그 객체 리스트 반환
    @GET("diet_log/select_exercise_log.php")
    Call<ExerciseLogVO[]> callSelectExercise(
            @Query("c_seq") String c_seq,
            @Query("e_date") String e_date
    );


    // 식단 기록 삽입
    @FormUrlEncoded
    @POST("diet_log/insert_diet_log.php")
    Call<Boolean> callInsertDietLog(

            @Field("c_seq")String c_seq,
            @Field("d_date") String d_date,
            @Field("d_img") String d_img,
            @Field("d_contents") String d_contents,
            @Field("d_period") String d_period,
            @Field("d_meal") String d_meal,
            @Field("d_kcal")String d_kcal

    );

    // 운동기록 삽입
    @FormUrlEncoded
    @POST("diet_log/insert_exercise_log.php")
    Call<Boolean> callInsertExerciseLog(

            @Field("c_seq")String c_seq,
            @Field("e_date") String d_period,
            @Field("e_img") String e_img,
            @Field("e_contents") String e_contents,
            @Field("e_weight") String e_weight,
            @Field("e_height")String e_height,
            @Field("e_fat")String e_fat,
            @Field("e_part")String e_part,
            @Field("e_minute")String e_minute
    );


    // 식단기록 업데이트
    @FormUrlEncoded
    @POST("diet_log/update_diet_log.php")
    Call<Boolean> callUpdateDietLog(

            @Field("d_seq")String c_seq,
            @Field("d_date") String d_date,
            @Field("d_img") String d_img,
            @Field("d_contents") String d_contents,
            @Field("d_period") String d_period,
            @Field("d_meal") String d_meal,
            @Field("d_kcal")String d_kcal

    );


    //운동기록 업데이트
    @FormUrlEncoded
    @POST("diet_log/update_exercise_log.php")
    Call<Boolean> callUpdateExerciseLog(

            @Field("e_seq")String c_seq,
            @Field("e_date") String d_period,
            @Field("e_img") String e_img,
            @Field("e_contents") String e_contents,
            @Field("e_weight") String e_weight,
            @Field("e_height")String e_height,
            @Field("e_fat")String e_fat,
            @Field("e_part")String e_part,
            @Field("e_minute")String e_minute

    );

    //식단기록 삭제
    @FormUrlEncoded
    @POST("diet_log/delete_diet_log.php")
    Call<Boolean> callDeleteDietLog(

            @Field("d_seq")String d_seq

    );

    //운동기록 삭제
    @FormUrlEncoded
    @POST("diet_log/delete_exercise_log.php")
    Call<Boolean> callDeleteExerciseLog(

            @Field("e_seq")String e_seq

    );

    //다이어트로그 포인트 검사

    @GET("diet_log/check_diet_point.php")
    Call<Boolean> callCheckDietPoint(
            @Query("c_seq") String c_seq
    );


    //다이어트로그 마지막행+1을 불러옴
    @GET("diet_log/select_max_d_seq.php")
    Call<String> callSelectDietMax(
    );
    //운동로그 마지막행+1을 불러옴
    @GET("diet_log/select_max_e_seq.php")
    Call<String> callSelectExerciseMax(
    );

    //포인트 리스트 조회
    @GET("mypage/select_point_list.php")
    Call<PointGarlicVO[]> callSelectPointGarlic(
            @Query("c_seq") String c_seq
    );


    @GET("mypage/select_review_list.php")
    Call<PdJoinReviewVO[]> callSelectPdJoinReviewVO(
            @Query("c_seq") String c_seq
    );
    /**추가된부분********************************************************/

    /**
     * 마이페이지 리뷰 삭제
     * @param re_seq
     * @return
     */
    @FormUrlEncoded
    @POST("mypage/delete_review_list.php")
    Call<Boolean> callDeleteReviewList(
            @Field("re_seq")String re_seq
    );


    @GET("product/select_order_max.php")
    Call<String> callSelectOrderMax(
            @Query("c_seq") int c_seq
    );


    @GET("product/select_product_by_seq.php")
    Call<ProductVO> callSelectProductBySeq(
            @Query("p_seq") int p_seq
    );

    //회원 포인트 감소
    @FormUrlEncoded
    @POST("diet_log/update_minus_customer_point.php")
    Call<Boolean> callUpdateMinusCustomerPoint(
            @Field("c_seq")String c_seq,
            @Field("c_point")String c_point

    );

    // 회원번호로 오더내역 조회
    @GET("mypage/SelectOrderByCSEQ.php")
    Call <OrderList2VO[]> callSelectOrderByCSEQ(
            @Query("c_seq") int c_seq
    );

    @GET("dbconTest.php")
    Call<Boolean> dbconTest(
    );





    // 쿠폰가격 가져오기
    @GET("product/SelectCouponPrice.php")
    Call <Integer> callSelectCouponPrice(
            @Query("cp_name") String cp_name
    );


    // 고객 업데이트
    @FormUrlEncoded
    @POST("product/UpdateCustomer.php")
    Call <Void> callUpdateCustomer(
            @Query("c_seq") int c_seq,
            @Field("c_point") int c_point,
            @Field("c_useable_coupon") int c_useable_coupon
    );

    // 쿠폰 업데이트
    @FormUrlEncoded
    @POST("product/UpdateCoupon.php")
    Call <Void> callUpdateCoupon(
            @Query("c_seq") int c_seq,
            @Query("cp_name") String cp_name,
            @Field("cp_useable") int cp_useable
    );






    /**제작중...************************************************/
    //이미지 업로드
    @Multipart
    @POST("diet_log/upload_img.php")
    Call<Boolean> callUploadImg(
            @Part("location") String location,
            @Part MultipartBody.Part upload_file);







}
