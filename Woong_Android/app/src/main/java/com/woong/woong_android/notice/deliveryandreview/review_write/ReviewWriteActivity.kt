package com.woong.woong_android.notice.deliveryandreview.review_write

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.RatingBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.woong.woong_android.R
import com.woong.woong_android.R.id.*
import com.woong.woong_android.applicationcontroller.ApplicationController
import com.woong.woong_android.myproduct.payment.dialog.PaymentDialog
import com.woong.woong_android.network.NetworkService
import com.woong.woong_android.notice.adapter.NtPhotoAdapter
import com.woong.woong_android.notice.data.PhotoData
import com.woong.woong_android.notice.deliveryandreview.review_write.dialog.ReviewRegisterDialog
import com.woong.woong_android.notice.post.PostReviewResponse
import com.woong.woong_android.notice.post.ReviewImageData
import com.woong.woong_android.notice.post.ReviewWriteData
import com.woong.woong_android.register_review
import com.woong.woong_android.woong_marketinfo
import com.woong.woong_android.woong_usertoken
import kotlinx.android.synthetic.main.activity_review_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class ReviewWriteActivity : AppCompatActivity(),RatingBar.OnRatingBarChangeListener {
    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        if(p0?.rating==1.0f){
            if(p0 == rating_speed_review){
                txt_speed_review.text="별로에요"
            }else if(p0 == rating_fresh_review){
                txt_fresh_review.text="별로에요"
            }else if(p0 == rating_kind_review){
                txt_kind_review.text="별로에요"
            }else if(p0==rating_taste_review){
                txt_taste_review.text="별로에요"
            }


        }else if(p0?.rating ==2.0f){

                if(p0 == rating_speed_review){
                    txt_speed_review.text="보통이에요"
                }else if(p0 == rating_fresh_review){
                    txt_fresh_review.text="보통이에요"
                }else if(p0 == rating_kind_review){
                    txt_kind_review.text="보통이에요"
                }else if(p0==rating_taste_review){
                    txt_taste_review.text="보통이에요"
                }

        }else if(p0?.rating == 3.0f){
            if(p0 == rating_speed_review){
                txt_speed_review.text="만족해요"
            }else if(p0 == rating_fresh_review){
                txt_fresh_review.text="만족해요"
            }else if(p0 == rating_kind_review){
                txt_kind_review.text="만족해요"
            }else if(p0==rating_taste_review){
                txt_taste_review.text="만족해요"
            }

        }else if(p0?.rating == 4.0f){
            if(p0 == rating_speed_review){
                txt_speed_review.text="정말좋아요"
            }else if(p0 == rating_fresh_review){
                txt_fresh_review.text="정말좋아요"
            }else if(p0 == rating_kind_review){
                txt_kind_review.text="정말좋아요"
            }else if(p0==rating_taste_review){
                txt_taste_review.text="정말좋아요"
            }
        }else if(p0?.rating == 5.0f){
            if(p0 == rating_speed_review){
                txt_speed_review.text="완벽해요"
            }else if(p0 == rating_fresh_review){
                txt_fresh_review.text="완벽해요"
            }else if(p0 == rating_kind_review){
                txt_kind_review.text="완벽해요"
            }else if(p0==rating_taste_review){
                txt_taste_review.text="완벽해요"
            }
        }

    }

    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    private var image : MultipartBody.Part? = null
    var btn_num = 0;

    lateinit var photoItems : ArrayList<PhotoData>
    lateinit var totalItems : ArrayList<PhotoData>
    lateinit var ntPhotoAdapter: NtPhotoAdapter
    lateinit var requestManager: RequestManager
    lateinit var reviewImageItems: ArrayList<ReviewImageData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)
        reviewImageItems=ArrayList()
        var flag = 0

        var displayMetrics = applicationContext.resources.displayMetrics
        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels


        var review_register_dialog = ReviewRegisterDialog(this)

//                ratingbar.setStarSize(20, Dimension.DP);


        var wm : WindowManager.LayoutParams = review_register_dialog.window.attributes
        wm.copyFrom(review_register_dialog.window.attributes)
//        wm.width = width-200
//        wm.height = height-600

        profileimg_message_notice.setImageResource(register_review.product_img)
        marketname_alarm.text = register_review.market_name
        productname_alarm.text = register_review.product_name


        btn_register_review.setOnClickListener {
            var networkService: NetworkService = ApplicationController.instance.networkService

            val reviewWriteData = ReviewWriteData(rating_speed_review.numStars, rating_taste_review.numStars,
                    rating_fresh_review.numStars, rating_kind_review.numStars, et_content_review.text.toString(), reviewImageItems)
//            reviewWriteData.rate_fresh=

            val postReview = networkService.postReview(woong_usertoken.user_token,register_review.market_id,reviewWriteData)
            postReview.enqueue(object: retrofit2.Callback<PostReviewResponse>{
                override fun onFailure(call: Call<PostReviewResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT)
                }

                override fun onResponse(call: Call<PostReviewResponse>?, response: Response<PostReviewResponse>?) {
                    if (response!!.isSuccessful) {
                        review_register_dialog.show()
                    }
                }
            })
            review_register_dialog.show()
        }


        rating_speed_review.onRatingBarChangeListener = this
        rating_fresh_review.onRatingBarChangeListener = this
        rating_kind_review.onRatingBarChangeListener = this
        rating_taste_review.onRatingBarChangeListener = this

        requestManager = Glide.with(this)
        photoItems = ArrayList()
        add_photo_review.setOnClickListener {
           changeImage()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data //그이미지의Uri를 가져옴
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                        Log.v("이미지", this.data.toString())
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!

                    //이거 서버에 보내줄때 필요 image = MultipartBody.Part.createFormData("photo", photo.name, photoBody) //여기의 photo는 키값의 이름하고 같아야함

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    photoItems.add(PhotoData(data.data))
                    reviewImageItems.add(ReviewImageData(data.data.toString()))

                    ntPhotoAdapter = NtPhotoAdapter(photoItems,requestManager)
                    rv_photo_review.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                    rv_photo_review.adapter = ntPhotoAdapter

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
    fun changeImage() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent,REQ_CODE_SELECT_IMAGE)
    }
}
