# 测试用例
## 1. GET /incident 查询事件列表接口
1.testGetIncidents_NormalCase_Page0Size10：
+ 测试请求第一页，每页 10 条记录。
+ 验证返回的数据和分页信息是否正确。

2.testGetIncidents_NormalCase_Page1Size10：
+ 测试请求第二页，每页 10 条记录。
+ 验证返回的数据和分页信息是否正确。

3.testGetIncidents_BoundaryCase_Page0Size0：
+ 测试请求第一页，每页 0 条记录。
+ 验证返回的空数据集和分页信息是否正确。

4.testGetIncidents_BoundaryCase_Page100Size10：
+ 测试请求不存在的页码。
+ 验证返回的空数据集和分页信息是否正确。

5.testGetIncidents_ExceptionCase_ServiceThrowsException：
+ 测试服务层抛出异常的情况。
+ 验证接口能否正确处理异常并返回错误信息

## 2. POST /incident 新增事件接口
1. testCreateIncident_NormalCase
+ 正常新增
  2.testCreateIncident_ExceptionCase_MissingRequiredField
+ 提供无效的 Incident 数据（例如缺少必填字段）。
  3.testCreateIncident_ExceptionCase_InvalidDateFormat
+ 提供格式不正确的 Incident（例如状态的类型不是数值）。
  4.testCreateIncident_ExceptionCase_OutOfRangeStatus
+ 提供超出范围的 Incident 数据（例如状态值不在允许范围内）

## 3.PUT /incident/{id} 更新事件接口
1. testUpdateIncident_NormalCase
+ 正常情况：提供有效的 Incident 数据和存在的 ID。
3. testUpdateIncident_BoundaryCase_NonExistentId
+ 边界条件：提供不存在的 ID。
3. 异常情况：
+ testUpdateIncident_ExceptionCase_MissingRequiredField
    + 提供无效的 Incident 数据（例如缺少必填字段）。
+ testUpdateIncident_ExceptionCase_InvalidDateFormat
    + 提供格式不正确的 Incident 数据（例如状态的类型不是数值）。
+ testUpdateIncident_ExceptionCase_OutOfRangeStatus
    + 提供超出范围的 Incident 数据（例如状态值不在允许范围内）。

## 4. DELETE /incident/{id} 删除事件接口
1. testDeleteIncident_NormalCase
+ 提供存在的 ID，成功删除指定 ID 的 Incident。
2.  testDeleteIncident_BoundaryCase_NonExistentId
+ 边界条件：
  +提供不存在的 ID。

### 测试依据
业务需求：确保接口返回的数据结构和内容符合业务需求。  
API 文档：根据 API 文档定义的输入输出格式进行测试。  
边界条件：考虑各种边界条件，确保接口的鲁棒性。  