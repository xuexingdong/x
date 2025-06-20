-- MSW游戏数据库初始化脚本
-- 创建时间: 2024

-- 创建怪物表
CREATE TABLE IF NOT EXISTS mob (
    id              BIGINT PRIMARY KEY,
    create_time     TIMESTAMP    NOT NULL,
    update_time     TIMESTAMP    NOT NULL,
    deleted         BOOLEAN      NOT NULL,
    code            VARCHAR(50)  NOT NULL,
    name            VARCHAR(255) NOT NULL,
    level           INT          NULL,
    max_hp          INT          NULL,
    max_mp          INT          NULL,
    exp             INT          NULL,
    physical_defense INT         NULL,
    magical_defense INT          NULL,
    evasion         INT          NULL,
    accuracy_requirement VARCHAR(255) NULL,
    image_path      VARCHAR(255) NULL,
    additional_info VARCHAR(255) NULL
);

-- 创建物品表
CREATE TABLE IF NOT EXISTS item (
    id              BIGINT PRIMARY KEY,
    create_time     TIMESTAMP    NOT NULL,
    update_time     TIMESTAMP    NOT NULL,
    deleted         BOOLEAN      NOT NULL,
    code            VARCHAR(50)  NOT NULL,
    name            VARCHAR(255) NOT NULL,
    item_type       VARCHAR(50)  NULL,
    description     TEXT         NULL
);

-- 创建怪物掉落表
CREATE TABLE IF NOT EXISTS mob_drop (
    id              BIGINT PRIMARY KEY,
    create_time     TIMESTAMP    NOT NULL,
    update_time     TIMESTAMP    NOT NULL,
    deleted         BOOLEAN      NOT NULL,
    mob_code        VARCHAR(50)  NOT NULL,
    item_code       VARCHAR(50)  NOT NULL,
    drop_rate       DECIMAL(5,4) NULL DEFAULT 0.0000
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_mob_create_time ON mob(create_time);
CREATE INDEX IF NOT EXISTS idx_mob_update_time ON mob(update_time);
CREATE INDEX IF NOT EXISTS idx_mob_code ON mob(code);
CREATE INDEX IF NOT EXISTS idx_mob_name ON mob(name);

CREATE INDEX IF NOT EXISTS idx_item_create_time ON item(create_time);
CREATE INDEX IF NOT EXISTS idx_item_update_time ON item(update_time);
CREATE INDEX IF NOT EXISTS idx_item_code ON item(code);
CREATE INDEX IF NOT EXISTS idx_item_name ON item(name);

CREATE INDEX IF NOT EXISTS idx_mob_drop_create_time ON mob_drop(create_time);
CREATE INDEX IF NOT EXISTS idx_mob_drop_update_time ON mob_drop(update_time);
CREATE INDEX IF NOT EXISTS idx_mob_drop_mob_code ON mob_drop(mob_code);
CREATE INDEX IF NOT EXISTS idx_mob_drop_item_code ON mob_drop(item_code);

-- 插入怪物数据
INSERT INTO mob (id, create_time, update_time, deleted, code, name, level, max_hp, max_mp, exp, physical_defense, magical_defense, evasion, accuracy_requirement, image_path, additional_info) VALUES
(1, NOW(), NOW(), FALSE, 'MOB001', '嫩寶', 1, 8, 0, 3, 0, 0, 0, '0 (每少1級 +0)', '0100100.img.xml', ''),
(2, NOW(), NOW(), FALSE, 'MOB002', '藍寶', 2, 15, 15, 4, 0, 0, 0, '0 (每少1級 +0)', '0100101.img.xml', ''),
(3, NOW(), NOW(), FALSE, 'MOB003', '提諾', 1, 9, 0, 4, 0, 0, 0, '0 (每少1級 +0)', '0100120.img.xml', ''),
(4, NOW(), NOW(), FALSE, 'MOB004', '提弗', 3, 25, 0, 7, 0, 0, 0, '0 (每少1級 +0)', '0100121.img.xml', ''),
(5, NOW(), NOW(), FALSE, 'MOB005', '提姆', 5, 45, 0, 9, 3, 10, 0, '0 (每少1級 +0)', '0100122.img.xml', ''),
(6, NOW(), NOW(), FALSE, 'MOB006', '提魯', 7, 70, 0, 16, 5, 20, 0, '0 (每少1級 +0)', '0100123.img.xml', ''),
(7, NOW(), NOW(), FALSE, 'MOB007', '提古爾', 9, 100, 0, 19, 10, 15, 1, '4 (每少1級 +0.133)', '0100124.img.xml', ''),
(8, NOW(), NOW(), FALSE, 'MOB008', '穆魯', 1, 8, 0, 1, 0, 0, 0, '0 (每少1級 +0)', '0100130.img.xml', ''),
(9, NOW(), NOW(), FALSE, 'MOB009', '穆魯帕', 3, 28, 0, 6, 0, 0, 0, '0 (每少1級 +0)', '0100131.img.xml', ''),
(10, NOW(), NOW(), FALSE, 'MOB010', '穆魯菲亞', 5, 43, 0, 9, 3, 10, 0, '0 (每少1級 +0)', '0100132.img.xml', ''),
(11, NOW(), NOW(), FALSE, 'MOB011', '穆魯穆魯', 7, 70, 0, 15, 5, 20, 0, '0 (每少1級 +0)', '0100133.img.xml', ''),
(12, NOW(), NOW(), FALSE, 'MOB012', '穆魯君', 9, 95, 0, 18, 10, 15, 1, '4 (每少1級 +0.133)', '0100134.img.xml', ''),
(13, NOW(), NOW(), FALSE, 'MOB013', '菇菇仔', 2, 20, 15, 5, 0, 0, 0, '0 (每少1級 +0)', '0120100.img.xml', ''),
(14, NOW(), NOW(), FALSE, 'MOB014', '木妖', 4, 40, 30, 8, 0, 10, 0, '0 (每少1級 +0)', '0130100.img.xml', 'F3'),
(15, NOW(), NOW(), FALSE, 'MOB015', '紅寶', 4, 40, 30, 8, 3, 10, 0, '0 (每少1級 +0)', '0130101.img.xml', ''),
(16, NOW(), NOW(), FALSE, 'MOB016', '綠水靈', 6, 50, 35, 10, 5, 10, 1, '4 (每少1級 +0.133)', '0210100.img.xml', 'L3'),
(17, NOW(), NOW(), FALSE, 'MOB017', '綠菇菇', 15, 250, 25, 26, 12, 40, 0, '0 (每少1級 +0)', '0130102.img.xml', ''),
(18, NOW(), NOW(), FALSE, 'MOB018', '三眼章魚', 10, 180, 50, 30, 8, 15, 5, '0 (每少1級 +0)', '0210101.img.xml', ''),
(19, NOW(), NOW(), FALSE, 'MOB019', '藍菇菇', 12, 220, 60, 35, 10, 18, 8, '0 (每少1級 +0)', '0130103.img.xml', ''),
(20, NOW(), NOW(), FALSE, 'MOB020', '木面怪人', 14, 300, 80, 40, 15, 20, 10, '0 (每少1級 +0)', '0140100.img.xml', ''),
(21, NOW(), NOW(), FALSE, 'MOB021', '蝴蝶精', 8, 120, 100, 25, 5, 25, 15, '0 (每少1級 +0)', '0150100.img.xml', ''),
(22, NOW(), NOW(), FALSE, 'MOB022', '風獨眼獸', 18, 500, 120, 80, 25, 30, 20, '0 (每少1級 +0)', '0160100.img.xml', ''),
(23, NOW(), NOW(), FALSE, 'MOB023', '鋼之肥肥', 20, 600, 150, 100, 30, 35, 25, '0 (每少1級 +0)', '0170100.img.xml', ''),
(24, NOW(), NOW(), FALSE, 'MOB024', '木乃伊犬', 16, 400, 90, 60, 20, 25, 18, '0 (每少1級 +0)', '0180100.img.xml', '');

-- 插入物品数据（基于item.json的部分数据）
INSERT INTO item (id, create_time, update_time, deleted, code, name, item_type, description) VALUES
(1, NOW(), NOW(), FALSE, 'ITEM001', '嫩寶殼', 'MATERIAL', '嫩寶掉落的材料'),
(2, NOW(), NOW(), FALSE, 'ITEM002', '紅色藥水', 'POTION', '恢复生命值的药水'),
(3, NOW(), NOW(), FALSE, 'ITEM003', '青蘋果', 'FOOD', '可食用的水果'),
(4, NOW(), NOW(), FALSE, 'ITEM004', '石榴石母礦', 'MATERIAL', '矿物材料'),
(5, NOW(), NOW(), FALSE, 'ITEM005', '箭矢', 'ARROW', '弓箭手使用的箭矢'),
(6, NOW(), NOW(), FALSE, 'ITEM006', '綠髮帶', 'ACCESSORY', '装饰品'),
(7, NOW(), NOW(), FALSE, 'ITEM007', '青銅母礦', 'MATERIAL', '矿物材料'),
(8, NOW(), NOW(), FALSE, 'ITEM008', '弩箭矢', 'ARROW', '弩箭手使用的箭矢'),
(9, NOW(), NOW(), FALSE, 'ITEM009', '披風魔防卷軸60%', 'SCROLL', '强化卷轴'),
(10, NOW(), NOW(), FALSE, 'ITEM010', '頭盔防禦卷軸10%', 'SCROLL', '强化卷轴'),
(11, NOW(), NOW(), FALSE, 'ITEM011', '綠液球', 'MATERIAL', '绿水灵掉落的材料'),
(12, NOW(), NOW(), FALSE, 'ITEM012', '綠水靈五目石', 'MATERIAL', '绿水灵掉落的特殊材料'),
(13, NOW(), NOW(), FALSE, 'ITEM013', '披風敏捷卷軸100%', 'SCROLL', '强化卷轴'),
(14, NOW(), NOW(), FALSE, 'ITEM014', '綠水靈珠', 'MATERIAL', '绿水灵掉落的珠子'),
(15, NOW(), NOW(), FALSE, 'ITEM015', '褲、裙防禦卷軸10%', 'SCROLL', '强化卷轴'),
(16, NOW(), NOW(), FALSE, 'ITEM016', '白頭巾', 'HELMET', '头部装备'),
(17, NOW(), NOW(), FALSE, 'ITEM017', '遺失的斧頭', 'WEAPON', '武器'),
(18, NOW(), NOW(), FALSE, 'ITEM018', '紫礦石母礦', 'MATERIAL', '矿物材料'),
(19, NOW(), NOW(), FALSE, 'ITEM019', '藍寶石母礦', 'MATERIAL', '矿物材料'),
(20, NOW(), NOW(), FALSE, 'ITEM020', '叉槍', 'WEAPON', '长柄武器'),
(21, NOW(), NOW(), FALSE, 'ITEM021', '褐安全鞋', 'SHOES', '鞋子装备'),
(22, NOW(), NOW(), FALSE, 'ITEM022', '魔法手套', 'GLOVES', '手套装备'),
(23, NOW(), NOW(), FALSE, 'ITEM023', '皮製手提包', 'ACCESSORY', '装饰品'),
(24, NOW(), NOW(), FALSE, 'ITEM024', '鐵製斧頭', 'WEAPON', '武器'),
(25, NOW(), NOW(), FALSE, 'ITEM025', '鐵指虎', 'WEAPON', '武器'),
(26, NOW(), NOW(), FALSE, 'ITEM026', '章魚腳', 'MATERIAL', '三眼章魚掉落的材料'),
(27, NOW(), NOW(), FALSE, 'ITEM027', '三眼章魚五目石', 'MATERIAL', '三眼章魚掉落的特殊材料'),
(28, NOW(), NOW(), FALSE, 'ITEM028', '單手劍攻擊卷軸10%', 'SCROLL', '强化卷轴'),
(29, NOW(), NOW(), FALSE, 'ITEM029', '單邊銀色耳環', 'ACCESSORY', '装饰品'),
(30, NOW(), NOW(), FALSE, 'ITEM030', '木錘', 'WEAPON', '武器'),
(31, NOW(), NOW(), FALSE, 'ITEM031', '鋼盾', 'SHIELD', '盾牌'),
(32, NOW(), NOW(), FALSE, 'ITEM032', '朱礦石母礦', 'MATERIAL', '矿物材料'),
(33, NOW(), NOW(), FALSE, 'ITEM033', '海藍石母礦', 'MATERIAL', '矿物材料'),
(34, NOW(), NOW(), FALSE, 'ITEM034', '藍色藥水', 'POTION', '恢复魔法值的药水'),
(35, NOW(), NOW(), FALSE, 'ITEM035', '黃錦絲緊身裙', 'CLOTHES', '服装'),
(36, NOW(), NOW(), FALSE, 'ITEM036', '黃錦絲緊身衣', 'CLOTHES', '服装'),
(37, NOW(), NOW(), FALSE, 'ITEM037', '青銅胸甲', 'ARMOR', '防具'),
(38, NOW(), NOW(), FALSE, 'ITEM038', '褐色鐵板褲子', 'CLOTHES', '服装'),
(39, NOW(), NOW(), FALSE, 'ITEM039', '加固皮護手', 'GLOVES', '手套装备'),
(40, NOW(), NOW(), FALSE, 'ITEM040', '藍菇菇傘', 'MATERIAL', '蓝菇菇掉落的材料'),
(41, NOW(), NOW(), FALSE, 'ITEM041', '橘色藥水', 'POTION', '恢复体力的药水'),
(42, NOW(), NOW(), FALSE, 'ITEM042', '魔力藥水', 'POTION', '恢复魔力的药水'),
(43, NOW(), NOW(), FALSE, 'ITEM043', '雙手劍攻擊卷軸10%', 'SCROLL', '强化卷轴'),
(44, NOW(), NOW(), FALSE, 'ITEM044', '戰鬥短刀', 'WEAPON', '武器'),
(45, NOW(), NOW(), FALSE, 'ITEM045', '鋼鐵鎧甲', 'ARMOR', '防具'),
(46, NOW(), NOW(), FALSE, 'ITEM046', '紅色自由帽', 'HELMET', '头部装备'),
(47, NOW(), NOW(), FALSE, 'ITEM047', '黃金母礦', 'MATERIAL', '矿物材料'),
(48, NOW(), NOW(), FALSE, 'ITEM048', '鋰礦戟', 'WEAPON', '长柄武器'),
(49, NOW(), NOW(), FALSE, 'ITEM049', '紅硬皮裝', 'ARMOR', '防具'),
(50, NOW(), NOW(), FALSE, 'ITEM050', '紅硬皮裙', 'CLOTHES', '服装'),
(51, NOW(), NOW(), FALSE, 'ITEM051', '藍軍官鎧甲', 'ARMOR', '防具'),
(52, NOW(), NOW(), FALSE, 'ITEM052', '鋼鐵軍官裙', 'CLOTHES', '服装'),
(53, NOW(), NOW(), FALSE, 'ITEM053', '紫寶石鞋', 'SHOES', '鞋子装备'),
(54, NOW(), NOW(), FALSE, 'ITEM054', '黃晶母礦', 'MATERIAL', '矿物材料'),
(55, NOW(), NOW(), FALSE, 'ITEM055', '白色藥水', 'POTION', '全能恢复药水'),
(56, NOW(), NOW(), FALSE, 'ITEM056', '鋰礦戰鬥鞋', 'SHOES', '鞋子装备'),
(57, NOW(), NOW(), FALSE, 'ITEM057', '半月刃', 'WEAPON', '武器'),
(58, NOW(), NOW(), FALSE, 'ITEM058', '法師長杖', 'WEAPON', '法师武器'),
(59, NOW(), NOW(), FALSE, 'ITEM059', '綠防風帽', 'HELMET', '头部装备'),
(60, NOW(), NOW(), FALSE, 'ITEM060', '灰色萊克帽', 'HELMET', '头部装备'),
(61, NOW(), NOW(), FALSE, 'ITEM061', '褐色水手頭巾', 'HELMET', '头部装备'),
(62, NOW(), NOW(), FALSE, 'ITEM062', '褐色皮茲頭巾', 'HELMET', '头部装备'),
(63, NOW(), NOW(), FALSE, 'ITEM063', '木板', 'MATERIAL', '木面怪人掉落的材料'),
(64, NOW(), NOW(), FALSE, 'ITEM064', '紫水晶母礦', 'MATERIAL', '矿物材料'),
(65, NOW(), NOW(), FALSE, 'ITEM065', '披風智力卷軸60%', 'SCROLL', '强化卷轴'),
(66, NOW(), NOW(), FALSE, 'ITEM066', '褐羽翼帽', 'HELMET', '头部装备'),
(67, NOW(), NOW(), FALSE, 'ITEM067', '鋰礦海盜頭盔', 'HELMET', '头部装备'),
(68, NOW(), NOW(), FALSE, 'ITEM068', '盾牌防禦卷軸60%', 'SCROLL', '强化卷轴'),
(69, NOW(), NOW(), FALSE, 'ITEM069', '藍伽藍', 'CLOTHES', '服装'),
(70, NOW(), NOW(), FALSE, 'ITEM070', '綠色自由帽', 'HELMET', '头部装备'),
(71, NOW(), NOW(), FALSE, 'ITEM071', '黑伽藍褲', 'CLOTHES', '服装'),
(72, NOW(), NOW(), FALSE, 'ITEM072', '黑追擊鞋', 'SHOES', '鞋子装备'),
(73, NOW(), NOW(), FALSE, 'ITEM073', '金戰鬥鞋', 'SHOES', '鞋子装备'),
(74, NOW(), NOW(), FALSE, 'ITEM074', '銀暴風手套', 'GLOVES', '手套装备'),
(75, NOW(), NOW(), FALSE, 'ITEM075', '鋼短刀', 'WEAPON', '武器'),
(76, NOW(), NOW(), FALSE, 'ITEM076', '紫礦機器手套', 'GLOVES', '手套装备'),
(77, NOW(), NOW(), FALSE, 'ITEM077', '藍賢者手套', 'GLOVES', '手套装备'),
(78, NOW(), NOW(), FALSE, 'ITEM078', '槍命中卷軸60%', 'SCROLL', '强化卷轴'),
(79, NOW(), NOW(), FALSE, 'ITEM079', '指虎命中卷軸100%', 'SCROLL', '强化卷轴'),
(80, NOW(), NOW(), FALSE, 'ITEM080', '彈丸', 'BULLET', '子弹'),
(81, NOW(), NOW(), FALSE, 'ITEM081', '蝴蝶精的觸角', 'MATERIAL', '蝴蝶精掉落的材料'),
(82, NOW(), NOW(), FALSE, 'ITEM082', '銀的母礦', 'MATERIAL', '矿物材料'),
(83, NOW(), NOW(), FALSE, 'ITEM083', '蛋白石母礦', 'MATERIAL', '矿物材料'),
(84, NOW(), NOW(), FALSE, 'ITEM084', '解毒劑', 'POTION', '解毒药剂'),
(85, NOW(), NOW(), FALSE, 'ITEM085', '眼藥水', 'POTION', '眼药水'),
(86, NOW(), NOW(), FALSE, 'ITEM086', '精力劑', 'POTION', '精力恢复剂'),
(87, NOW(), NOW(), FALSE, 'ITEM087', '聖水', 'POTION', '圣水'),
(88, NOW(), NOW(), FALSE, 'ITEM088', '萬能療傷藥', 'POTION', '万能治疗药'),
(89, NOW(), NOW(), FALSE, 'ITEM089', '鋰礦石母礦', 'MATERIAL', '矿物材料'),
(90, NOW(), NOW(), FALSE, 'ITEM090', '風獨眼獸之尾巴', 'MATERIAL', '风独眼兽掉落的材料'),
(91, NOW(), NOW(), FALSE, 'ITEM091', '弓攻擊卷軸10%', 'SCROLL', '强化卷轴'),
(92, NOW(), NOW(), FALSE, 'ITEM092', '棒棒果', 'FOOD', '可食用果实'),
(93, NOW(), NOW(), FALSE, 'ITEM093', '子彈', 'BULLET', '子弹'),
(94, NOW(), NOW(), FALSE, 'ITEM094', '紅龍服', 'CLOTHES', '特殊服装'),
(95, NOW(), NOW(), FALSE, 'ITEM095', '黑武褲', 'CLOTHES', '服装'),
(96, NOW(), NOW(), FALSE, 'ITEM096', '綠星魔法帽', 'HELMET', '魔法头盔'),
(97, NOW(), NOW(), FALSE, 'ITEM097', '黑防風帽', 'HELMET', '头部装备'),
(98, NOW(), NOW(), FALSE, 'ITEM098', '黑暗影之服', 'CLOTHES', '特殊服装'),
(99, NOW(), NOW(), FALSE, 'ITEM099', '黑暗影之褲', 'CLOTHES', '特殊服装'),
(100, NOW(), NOW(), FALSE, 'ITEM100', '刺槍', 'WEAPON', '长柄武器');

-- 插入怪物掉落关系数据
INSERT INTO mob_drop (id, create_time, update_time, deleted, mob_code, item_code, drop_rate) VALUES
-- 嫩寶掉落物品
(1, NOW(), NOW(), FALSE, 'MOB001', 'ITEM001', 0.1500),
(2, NOW(), NOW(), FALSE, 'MOB001', 'ITEM002', 0.1200),
(3, NOW(), NOW(), FALSE, 'MOB001', 'ITEM003', 0.1000),
(4, NOW(), NOW(), FALSE, 'MOB001', 'ITEM004', 0.0800),
(5, NOW(), NOW(), FALSE, 'MOB001', 'ITEM005', 0.0600),
(6, NOW(), NOW(), FALSE, 'MOB001', 'ITEM006', 0.0500),
(7, NOW(), NOW(), FALSE, 'MOB001', 'ITEM007', 0.0700),
(8, NOW(), NOW(), FALSE, 'MOB001', 'ITEM008', 0.0600),
(9, NOW(), NOW(), FALSE, 'MOB001', 'ITEM009', 0.0300),
(10, NOW(), NOW(), FALSE, 'MOB001', 'ITEM010', 0.0200),

-- 綠水靈掉落物品
(11, NOW(), NOW(), FALSE, 'MOB016', 'ITEM011', 0.1800),
(12, NOW(), NOW(), FALSE, 'MOB016', 'ITEM012', 0.0500),
(13, NOW(), NOW(), FALSE, 'MOB016', 'ITEM013', 0.0400),
(14, NOW(), NOW(), FALSE, 'MOB016', 'ITEM014', 0.0600),
(15, NOW(), NOW(), FALSE, 'MOB016', 'ITEM002', 0.1200),
(16, NOW(), NOW(), FALSE, 'MOB016', 'ITEM015', 0.0300),
(17, NOW(), NOW(), FALSE, 'MOB016', 'ITEM016', 0.0800),
(18, NOW(), NOW(), FALSE, 'MOB016', 'ITEM017', 0.0200),
(19, NOW(), NOW(), FALSE, 'MOB016', 'ITEM018', 0.0700),
(20, NOW(), NOW(), FALSE, 'MOB016', 'ITEM019', 0.0700),
(21, NOW(), NOW(), FALSE, 'MOB016', 'ITEM020', 0.0600),
(22, NOW(), NOW(), FALSE, 'MOB016', 'ITEM021', 0.0500),
(23, NOW(), NOW(), FALSE, 'MOB016', 'ITEM022', 0.0400),
(24, NOW(), NOW(), FALSE, 'MOB016', 'ITEM023', 0.0300),
(25, NOW(), NOW(), FALSE, 'MOB016', 'ITEM024', 0.0500),
(26, NOW(), NOW(), FALSE, 'MOB016', 'ITEM003', 0.0800),
(27, NOW(), NOW(), FALSE, 'MOB016', 'ITEM025', 0.0400),
(28, NOW(), NOW(), FALSE, 'MOB016', 'ITEM005', 0.0600),
(29, NOW(), NOW(), FALSE, 'MOB016', 'ITEM008', 0.0600),

-- 三眼章魚掉落物品
(30, NOW(), NOW(), FALSE, 'MOB018', 'ITEM026', 0.2000),
(31, NOW(), NOW(), FALSE, 'MOB018', 'ITEM027', 0.0500),
(32, NOW(), NOW(), FALSE, 'MOB018', 'ITEM002', 0.1500),
(33, NOW(), NOW(), FALSE, 'MOB018', 'ITEM028', 0.0300),
(34, NOW(), NOW(), FALSE, 'MOB018', 'ITEM029', 0.0400),
(35, NOW(), NOW(), FALSE, 'MOB018', 'ITEM030', 0.0600),
(36, NOW(), NOW(), FALSE, 'MOB018', 'ITEM031', 0.0500),
(37, NOW(), NOW(), FALSE, 'MOB018', 'ITEM032', 0.0800),
(38, NOW(), NOW(), FALSE, 'MOB018', 'ITEM033', 0.0700),
(39, NOW(), NOW(), FALSE, 'MOB018', 'ITEM034', 0.1200),
(40, NOW(), NOW(), FALSE, 'MOB018', 'ITEM035', 0.0400),
(41, NOW(), NOW(), FALSE, 'MOB018', 'ITEM036', 0.0400),
(42, NOW(), NOW(), FALSE, 'MOB018', 'ITEM037', 0.0500),
(43, NOW(), NOW(), FALSE, 'MOB018', 'ITEM038', 0.0300),
(44, NOW(), NOW(), FALSE, 'MOB018', 'ITEM039', 0.0300),
(45, NOW(), NOW(), FALSE, 'MOB018', 'ITEM005', 0.0600),
(46, NOW(), NOW(), FALSE, 'MOB018', 'ITEM008', 0.0600),

-- 藍菇菇掉落物品
(47, NOW(), NOW(), FALSE, 'MOB019', 'ITEM040', 0.2000),
(48, NOW(), NOW(), FALSE, 'MOB019', 'ITEM041', 0.1500),
(49, NOW(), NOW(), FALSE, 'MOB019', 'ITEM042', 0.1200),
(50, NOW(), NOW(), FALSE, 'MOB019', 'ITEM043', 0.0300),
(51, NOW(), NOW(), FALSE, 'MOB019', 'ITEM044', 0.0500),
(52, NOW(), NOW(), FALSE, 'MOB019', 'ITEM045', 0.0400),
(53, NOW(), NOW(), FALSE, 'MOB019', 'ITEM046', 0.0600),
(54, NOW(), NOW(), FALSE, 'MOB019', 'ITEM005', 0.0600),
(55, NOW(), NOW(), FALSE, 'MOB019', 'ITEM047', 0.0800),
(56, NOW(), NOW(), FALSE, 'MOB019', 'ITEM019', 0.0700),
(57, NOW(), NOW(), FALSE, 'MOB019', 'ITEM034', 0.1200),
(58, NOW(), NOW(), FALSE, 'MOB019', 'ITEM048', 0.0500),
(59, NOW(), NOW(), FALSE, 'MOB019', 'ITEM049', 0.0400),
(60, NOW(), NOW(), FALSE, 'MOB019', 'ITEM050', 0.0400),
(61, NOW(), NOW(), FALSE, 'MOB019', 'ITEM051', 0.0400),
(62, NOW(), NOW(), FALSE, 'MOB019', 'ITEM052', 0.0300),
(63, NOW(), NOW(), FALSE, 'MOB019', 'ITEM008', 0.0600),
(64, NOW(), NOW(), FALSE, 'MOB019', 'ITEM053', 0.0300),
(65, NOW(), NOW(), FALSE, 'MOB019', 'ITEM020', 0.0500),
(66, NOW(), NOW(), FALSE, 'MOB019', 'ITEM054', 0.0700),
(67, NOW(), NOW(), FALSE, 'MOB019', 'ITEM055', 0.1000),
(68, NOW(), NOW(), FALSE, 'MOB019', 'ITEM056', 0.0300),
(69, NOW(), NOW(), FALSE, 'MOB019', 'ITEM057', 0.0400),
(70, NOW(), NOW(), FALSE, 'MOB019', 'ITEM058', 0.0300),
(71, NOW(), NOW(), FALSE, 'MOB019', 'ITEM059', 0.0400),
(72, NOW(), NOW(), FALSE, 'MOB019', 'ITEM024', 0.0500),
(73, NOW(), NOW(), FALSE, 'MOB019', 'ITEM060', 0.0300),
(74, NOW(), NOW(), FALSE, 'MOB019', 'ITEM061', 0.0200),
(75, NOW(), NOW(), FALSE, 'MOB019', 'ITEM062', 0.0200),

-- 木面怪人掉落物品
(76, NOW(), NOW(), FALSE, 'MOB020', 'ITEM063', 0.2000),
(77, NOW(), NOW(), FALSE, 'MOB020', 'ITEM041', 0.1500),
(78, NOW(), NOW(), FALSE, 'MOB020', 'ITEM064', 0.0800),
(79, NOW(), NOW(), FALSE, 'MOB020', 'ITEM033', 0.0700),
(80, NOW(), NOW(), FALSE, 'MOB020', 'ITEM007', 0.0700),
(81, NOW(), NOW(), FALSE, 'MOB020', 'ITEM065', 0.0300),
(82, NOW(), NOW(), FALSE, 'MOB020', 'ITEM066', 0.0400),
(83, NOW(), NOW(), FALSE, 'MOB020', 'ITEM067', 0.0300),
(84, NOW(), NOW(), FALSE, 'MOB020', 'ITEM068', 0.0300),
(85, NOW(), NOW(), FALSE, 'MOB020', 'ITEM069', 0.0500),
(86, NOW(), NOW(), FALSE, 'MOB020', 'ITEM070', 0.0400),
(87, NOW(), NOW(), FALSE, 'MOB020', 'ITEM071', 0.0300),
(88, NOW(), NOW(), FALSE, 'MOB020', 'ITEM072', 0.0300),
(89, NOW(), NOW(), FALSE, 'MOB020', 'ITEM073', 0.0300),
(90, NOW(), NOW(), FALSE, 'MOB020', 'ITEM074', 0.0300),
(91, NOW(), NOW(), FALSE, 'MOB020', 'ITEM075', 0.0400),
(92, NOW(), NOW(), FALSE, 'MOB020', 'ITEM076', 0.0200),
(93, NOW(), NOW(), FALSE, 'MOB020', 'ITEM077', 0.0200),
(94, NOW(), NOW(), FALSE, 'MOB020', 'ITEM078', 0.0200),
(95, NOW(), NOW(), FALSE, 'MOB020', 'ITEM034', 0.1200),
(96, NOW(), NOW(), FALSE, 'MOB020', 'ITEM079', 0.0200),
(97, NOW(), NOW(), FALSE, 'MOB020', 'ITEM080', 0.0500),
(98, NOW(), NOW(), FALSE, 'MOB020', 'ITEM061', 0.0300),

-- 蝴蝶精掉落物品
(99, NOW(), NOW(), FALSE, 'MOB021', 'ITEM081', 0.2000),
(100, NOW(), NOW(), FALSE, 'MOB021', 'ITEM041', 0.1500),
(101, NOW(), NOW(), FALSE, 'MOB021', 'ITEM034', 0.1200),
(102, NOW(), NOW(), FALSE, 'MOB021', 'ITEM082', 0.0800),
(103, NOW(), NOW(), FALSE, 'MOB021', 'ITEM064', 0.0700),
(104, NOW(), NOW(), FALSE, 'MOB021', 'ITEM032', 0.0700),
(105, NOW(), NOW(), FALSE, 'MOB021', 'ITEM054', 0.0700),
(106, NOW(), NOW(), FALSE, 'MOB021', 'ITEM018', 0.0600),
(107, NOW(), NOW(), FALSE, 'MOB021', 'ITEM033', 0.0600),
(108, NOW(), NOW(), FALSE, 'MOB021', 'ITEM083', 0.0600),
(109, NOW(), NOW(), FALSE, 'MOB021', 'ITEM084', 0.1000),
(110, NOW(), NOW(), FALSE, 'MOB021', 'ITEM085', 0.0800),
(111, NOW(), NOW(), FALSE, 'MOB021', 'ITEM086', 0.0900),
(112, NOW(), NOW(), FALSE, 'MOB021', 'ITEM087', 0.0500),
(113, NOW(), NOW(), FALSE, 'MOB021', 'ITEM088', 0.0300),
(114, NOW(), NOW(), FALSE, 'MOB021', 'ITEM089', 0.0500);

-- 删除测试用语句，验证表结构
-- SELECT COUNT(*) FROM mob;
-- SELECT COUNT(*) FROM item;
-- SELECT COUNT(*) FROM mob_drop;

-- 查询特定怪物掉落物品示例
-- SELECT m.name as mob_name, i.name as item_name, md.drop_rate 
-- FROM mob_drop md 
-- JOIN mob m ON md.mob_code = m.code 
-- JOIN item i ON md.item_code = i.code 
-- WHERE m.name = '綠水靈';

-- 查询特定物品掉落来源示例
-- SELECT i.name as item_name, m.name as mob_name, md.drop_rate 
-- FROM mob_drop md 
-- JOIN mob m ON md.mob_code = m.code 
-- JOIN item i ON md.item_code = i.code 
-- WHERE i.name = '紅色藥水'; 