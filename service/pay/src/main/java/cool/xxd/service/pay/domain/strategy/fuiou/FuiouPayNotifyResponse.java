package cool.xxd.service.pay.domain.strategy.fuiou;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FuiouPayNotifyResponse extends BaseResponse {
    @XmlElement(name = "user_id")
    private String userId;

    @NotNull
    @XmlElement(name = "order_amt")
    private Integer orderAmt;

    @NotNull
    @XmlElement(name = "settle_order_amt")
    private Integer settleOrderAmt;

    @XmlElement(name = "curr_type")
    private String currType;

    @NotNull
    @XmlElement(name = "transaction_id")
    private String transactionId;

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @XmlElement(name = "order_type")
    private String orderType;

    @NotNull
    @XmlElement(name = "txn_fin_ts")
    private String txnFinTs;

    @NotNull
    @XmlElement(name = "reserved_fy_settle_dt")
    private String reservedFySettleDt;

    @XmlElement(name = "reserved_coupon_fee")
    private String reservedCouponFee;

    @XmlElement(name = "reserved_buyer_logon_id")
    private String reservedBuyerLogonId;

    @XmlElement(name = "reserved_fund_bill_list")
    private String reservedFundBillList;

    @XmlElement(name = "reserved_fy_trace_no")
    private String reservedFyTraceNo;

    @XmlElement(name = "reserved_channel_order_id")
    private String reservedChannelOrderId;

    @XmlElement(name = "reserved_is_credit")
    private String reservedIsCredit;

    @XmlElement(name = "reserved_addn_inf")
    private String reservedAddnInf;

    @XmlElement(name = "reserved_settlement_amt")
    private Integer reservedSettlementAmt;

    @XmlElement(name = "reserved_bank_type")
    private String reservedBankType;

    @XmlElement(name = "reserved_promotion_detail")
    private String reservedPromotionDetail;

    @XmlElement(name = "reserved_hb_is_seller")
    private String reservedHbIsSeller;

    @XmlElement(name = "reserved_service_charge_flag")
    private String reservedServiceChargeFlag;

    @XmlElement(name = "reserved_voucher_detail_list")
    private String reservedVoucherDetailList;

    @XmlElement(name = "reserved_discount_goods_detail")
    private String reservedDiscountGoodsDetail;

    @XmlElement(name = "reserved_sub_mchnt_cd")
    private String reservedSubMchntCd;
}
