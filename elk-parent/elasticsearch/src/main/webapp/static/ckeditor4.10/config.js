/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.language = 'zh-cn';/*将编辑器的语言设置为中文*/
	config.image_previewText = ' ';/*去掉图片预览框的文字*/
	config.removeDialogTabs = 'image:advanced;link:advanced';
	config.disallowedContent = 'img{width,height};img[width,height]';//图片自适应
};
