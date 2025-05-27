package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MicropayResponse extends BasePayResponse {

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "total_amount")
    private Integer totalAmount;

    @XmlElement(name = "buyer_id")
    private String buyerId;

    @NotNull
    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "addn_inf")
    private String addnInf;

    @NotNull
    @XmlElement(name = "reserved_mchnt_order_no")
    private String reservedMchntOrderNo;

    @NotNull
    @XmlElement(name = "reserved_fy_settle_dt")
    private String reservedFySettleDt;

    @XmlElement(name = "reserved_coupon_fee")
    private String reservedCouponFee;

    @XmlElement(name = "reserved_buyer_logon_id")
    private String reservedBuyerLogonId;

    @XmlElement(name = "reserved_fund_bill_list")
    private String reservedFundBillList;

    @XmlElement(name = "reserved_fy_term_id")
    private String reservedFyTermId;

    @XmlElement(name = "reserved_is_credit")
    private String reservedIsCredit;

    @XmlElement(name = "reserved_txn_fin_ts")
    private String reservedTxnFinTs;

    @XmlElement(name = "reserved_settlement_amt")
    private Integer reservedSettlementAmt;

    @XmlElement(name = "reserved_bank_type")
    private String reservedBankType;

    @XmlElement(name = "reserved_promotion_detail")
    private String reservedPromotionDetail;

    @XmlElement(name = "reserved_voucher_detail_list")
    private String reservedVoucherDetailList;

    @XmlElement(name = "reserved_discount_goods_detail")
    private String reservedDiscountGoodsDetail;

    @XmlElement(name = "reserved_hb_is_seller")
    private String reservedHbIsSeller;

    @XmlElement(name = "reserved_service_charge_flag")
    private String reservedServiceChargeFlag;

    @XmlElement(name = "reserved_sub_mchnt_cd")
    private String reservedSubMchntCd;

    @XmlElement(name = "reserved_trade_type")
    private String reservedTradeType;

}
