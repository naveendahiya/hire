(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireQuestionDeleteController',CareerPortalQuestionnaireQuestionDeleteController);

    CareerPortalQuestionnaireQuestionDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalQuestionnaireQuestion'];

    function CareerPortalQuestionnaireQuestionDeleteController($uibModalInstance, entity, CareerPortalQuestionnaireQuestion) {
        var vm = this;

        vm.careerPortalQuestionnaireQuestion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalQuestionnaireQuestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
